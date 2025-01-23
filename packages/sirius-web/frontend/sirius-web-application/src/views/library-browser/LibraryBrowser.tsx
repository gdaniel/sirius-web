/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useComponent } from '@eclipse-sirius/sirius-components-core';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import { makeStyles } from 'tss-react/mui';
import Typography from '@mui/material/Typography';
import { MaterialReactTable, MRT_ColumnDef, MRT_PaginationState, useMaterialReactTable } from 'material-react-table';
import { useState, useEffect, useMemo, memo } from 'react';
import { useLibraries } from './useLibraries';
import { GQLLibrary } from './useLibraries.types';
import { LibrariesTableProps } from './LibraryBrowser.types';

const useLibrariesViewStyle = makeStyles()((theme) => ({
  librariesView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(5),
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
}));

export const LibraryBrowser = () => {
  const { classes } = useLibrariesViewStyle();
  const { Component: Footer } = useComponent(footerExtensionPoint);

  const { data: libraries, totalLibrariesCount, refreshLibraries } = useLibraries(0, 10);

  const rows: GQLLibrary[] = libraries?.viewer.libraries.edges.map((edge) => edge.node) || [];

  return (
    <div className={classes.librariesView}>
      <NavigationBar />
      <Container maxWidth="xl">
        <Grid container justifyContent="center">
          <Grid item xs={8}>
            <main className={classes.main}>
              <div className={classes.header}>
                <Typography variant="h4">Existing Libraries</Typography>
              </div>
              <LibrariesTable rows={rows} rowCount={totalLibrariesCount} onPaginationChange={refreshLibraries} />
            </main>
          </Grid>
        </Grid>
      </Container>
      <Footer />
    </div>
  );
};

const LibrariesTable = memo(({ rows, rowCount, onPaginationChange }: LibrariesTableProps) => {
  const [pagination, setPagination] = useState<MRT_PaginationState>({
    pageIndex: 0,
    pageSize: 20,
  });

  useEffect(() => {
    onPaginationChange(pagination.pageIndex, pagination.pageSize);
  }, [pagination.pageIndex, pagination.pageSize]);

  const columns = useMemo<MRT_ColumnDef<GQLLibrary>[]>(
    () => [
      {
        accessorFn: (row) => row.name,
        header: 'Name',
        enableEditing: false,
        Cell: ({ renderedCellValue }) => renderedCellValue,
      },
      {
        accessorFn: (row) => row.version,
        header: 'Version',
        enableEditing: false,
        Cell: ({ renderedCellValue }) => <Typography variant="caption">{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => row.description,
        header: 'Description',
        enableEditing: false,
        Cell: ({ renderedCellValue }) => <Typography variant="caption">{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => row.projectId,
        header: 'Project ID',
        enableEditing: false,
        enableSorting: false,
        Cell: ({ renderedCellValue }) => <Typography variant="caption">{renderedCellValue}</Typography>,
      },
    ],
    []
  );

  const table = useMaterialReactTable<GQLLibrary>({
    // Data
    columns,
    data: rows,
    muiTableHeadCellProps: ({ column }) => ({
      sx: (theme) => ({
        color: column.columnDef.header === 'Project ID' ? theme.palette.text.disabled : 'inherit',
      }),
    }),
    muiTableBodyCellProps: ({ column }) => ({
      sx: (theme) => ({
        color: column.columnDef.header === 'Project ID' ? theme.palette.text.disabled : 'inherit',
      }),
    }),

    // Disable some unnecessary features (overkill here)
    enableColumnActions: false,
    enableColumnFilters: false,
    enableFullScreenToggle: false,
    enableDensityToggle: false,
    enableHiding: false,

    // Configure pagination
    enablePagination: true,
    manualPagination: true,
    onPaginationChange: setPagination,
    state: { pagination },
    rowCount,
  });

  return <MaterialReactTable table={table} />;
});
