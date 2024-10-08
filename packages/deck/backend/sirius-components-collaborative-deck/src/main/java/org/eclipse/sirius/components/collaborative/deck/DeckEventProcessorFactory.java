/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.deck;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventHandler;
import org.eclipse.sirius.components.collaborative.deck.service.DeckCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.deck.Deck;
import org.springframework.stereotype.Service;

/**
 * Used to create the deck event processors.
 *
 * @author fbarbin
 */
@Service
public class DeckEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationSearchService representationSearchService;

    private final DeckCreationService deckCreationService;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final List<IDeckEventHandler> deckEventHandlers;

    private final IRepresentationPersistenceService representationPersistenceService;

    public DeckEventProcessorFactory(IRepresentationSearchService representationSearchService, DeckCreationService deckCreationService, ISubscriptionManagerFactory subscriptionManagerFactory,
            List<IDeckEventHandler> deckEventHandlers, IRepresentationPersistenceService representationPersistenceService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.deckCreationService = Objects.requireNonNull(deckCreationService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.deckEventHandlers = Objects.requireNonNull(deckEventHandlers);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(representationId, List.of(Deck.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        var optionalDeck = this.representationSearchService.findById(editingContext, representationId, Deck.class);
        if (optionalDeck.isPresent()) {
            Deck deck = optionalDeck.get();
            DeckContext deckContext = new DeckContext(deck);

            IRepresentationEventProcessor deckEventProcessor = new DeckEventProcessor(editingContext, this.subscriptionManagerFactory.create(), this.deckCreationService, this.deckEventHandlers,
                    deckContext, this.representationPersistenceService, this.representationSearchService);

            return Optional.of(deckEventProcessor);
        }

        return Optional.empty();
    }
}
