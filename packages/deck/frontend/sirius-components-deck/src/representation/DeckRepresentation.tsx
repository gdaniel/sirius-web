/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import { ApolloError, OnDataOptions, useSubscription } from '@apollo/client';
import {
  RepresentationComponentProps,
  RepresentationLoadingIndicator,
  UseSelectionValue,
  useMultiToast,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { Theme, useTheme } from '@mui/material/styles';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { Deck } from '../Deck';
import { Card, CardMetadata, Lane } from '../Deck.types';
import {
  convertToTrelloDeckData,
  findLaneById,
  moveCardInDeckLanes,
  moveLaneInDeck,
  updateCard,
  updateLane,
} from '../utils/deckGQLHelper';
import { DeckRepresentationState } from './DeckRepresentation.types';
import { deckEventSubscription } from './deckSubscription';
import {
  GQLDeckEventPayload,
  GQLDeckEventSubscription,
  GQLDeckRefreshedEventPayload,
  GQLErrorPayload,
  GQLLane,
} from './deckSubscription.types';

import { flushSync } from 'react-dom';
import { useDeckMutations } from './useDeckMutations';

const useDeckRepresentationStyles = makeStyles()(() => ({
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

const isDeckRefreshedEventPayload = (payload: GQLDeckEventPayload): payload is GQLDeckRefreshedEventPayload =>
  payload.__typename === 'DeckRefreshedEventPayload';
const isErrorPayload = (payload: GQLDeckEventPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const DeckRepresentation = ({ editingContextId, representationId }: RepresentationComponentProps) => {
  const theme: Theme = useTheme();
  const { classes } = useDeckRepresentationStyles();
  const { selection, setSelection }: UseSelectionValue = useSelection();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ id, deck, complete }, setState] = useState<DeckRepresentationState>({
    id: crypto.randomUUID(),
    deck: undefined,
    complete: false,
  });

  const onData = ({ data }: OnDataOptions<GQLDeckEventSubscription>) => {
    flushSync(() => {
      if (data.data) {
        const { deckEvent } = data.data;
        if (isDeckRefreshedEventPayload(deckEvent)) {
          setState((prevState) => {
            return { ...prevState, deck: deckEvent.deck };
          });
        } else if (isErrorPayload(deckEvent)) {
          addMessages(deckEvent.messages);
        }
      }
    });
  };

  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true, deck: undefined }));

  useSubscription<GQLDeckEventSubscription>(deckEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        deckId: representationId,
      },
    },
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
    onError,
  });

  const { deleteCard, editDeckCard, createCard, dropDeckCard, editDeckLane, dropDeckLane, changeLaneCollapsedState } =
    useDeckMutations(editingContextId, representationId);

  useEffect(() => {
    if (deck && selection.entries) {
      const selectionIds: string[] = selection.entries.map((entry) => entry.id);
      const tempselectedElementIds: string[] = [];
      deck.lanes
        .flatMap((lane) => lane.cards)
        .forEach((card) => {
          if (selectionIds.includes(card.targetObjectId)) {
            tempselectedElementIds.push(card.id);
          }
        });
      setState((prevState) => {
        return { ...prevState, selectedElementIds: tempselectedElementIds };
      });
    }
  }, [selection]);

  const handleEditCard = (_laneId: string, card: Card) => {
    if (deck) {
      // to avoid blink because useMutation implies a re-render as the card value is the old one
      const updatedDeck = updateCard(deck, card);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
    }
    editDeckCard(card);
  };

  const handleEditLane = (laneId: string, newValue: { title: string }) => {
    if (deck) {
      // to avoid blink because useMutation implies a re-render as the lane value is the old one
      const updatedDeck = updateLane(deck, laneId, newValue.title);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
    }
    editDeckLane(laneId, newValue);
  };

  const handleCardClicked = (_cardId: string, metadata: CardMetadata, _laneId: string) => {
    setSelection({
      entries: [metadata.selection],
    });
  };

  const handleDropDeckCard = (oldLaneId: string, newLaneId: string, cardId: string, addedIndex: number) => {
    if (deck) {
      const updatedDeck = moveCardInDeckLanes(deck, oldLaneId, newLaneId, cardId, addedIndex);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
    }
    dropDeckCard(oldLaneId, newLaneId, cardId, addedIndex);
  };

  const handleLaneClicked = (laneId: string) => {
    if (deck) {
      const lane: GQLLane | undefined = findLaneById(deck, laneId);
      if (lane) {
        setSelection({
          entries: [
            {
              id: lane.targetObjectId,
            },
          ],
        });
      }
    }
  };

  const handleLaneDragEnd = (oldIndex: number, newIndex: number, lane: Lane) => {
    if (deck) {
      const updatedDeck = moveLaneInDeck(deck, oldIndex, newIndex);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
    }
    dropDeckLane(newIndex, lane);
  };

  let content: JSX.Element | null = null;
  if (complete) {
    content = (
      <div className={classes.complete}>
        <Typography variant="h5" align="center">
          The Deck does not exist anymore
        </Typography>
      </div>
    );
  } else if (!deck) {
    return <RepresentationLoadingIndicator />;
  } else {
    const selectedElementIds: string[] = selection.entries.map((entry) => entry.id);
    const data = convertToTrelloDeckData(deck, selectedElementIds, theme);
    content = (
      <Deck
        editingContextId={editingContextId}
        representationId={representationId}
        data={data}
        onCardClick={handleCardClicked}
        onCardDelete={deleteCard}
        onCardAdd={createCard}
        onCardUpdate={handleEditCard}
        onCardMoveAcrossLanes={handleDropDeckCard}
        onLaneClick={handleLaneClicked}
        onLaneUpdate={handleEditLane}
        onLaneCollapseUpdate={changeLaneCollapsedState}
        handleLaneDragEnd={handleLaneDragEnd}
      />
    );
  }
  return <>{content}</>;
};
