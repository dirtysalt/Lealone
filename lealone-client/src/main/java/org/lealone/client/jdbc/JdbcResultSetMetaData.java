/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.lealone.client.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.lealone.common.exceptions.DbException;
import org.lealone.common.trace.TraceObjectType;
import org.lealone.common.util.MathUtils;
import org.lealone.db.result.Result;
import org.lealone.db.value.DataType;

/**
 * Represents the meta data for a ResultSet.
 */
public class JdbcResultSetMetaData extends JdbcWrapper implements ResultSetMetaData {

    private final String catalog;
    private final JdbcPreparedStatement prep;
    private final JdbcResultSet rs;
    private final Result result;
    private final int columnCount;

    JdbcResultSetMetaData(JdbcConnection conn, String catalog, JdbcPreparedStatement prep,
            JdbcResultSet rs, Result result, int id) {
        this.catalog = catalog;
        this.prep = prep;
        this.rs = rs;
        this.result = result;
        this.columnCount = result.getVisibleColumnCount();
        this.trace = conn.getTrace(TraceObjectType.RESULT_SET_META_DATA, id);
    }

    /**
     * Returns the number of columns.
     *
     * @return the number of columns
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public int getColumnCount() throws SQLException {
        try {
            debugCodeCall("getColumnCount");
            checkClosed();
            return columnCount;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Returns the column label.
     *
     * @param column the column index (1,2,...)
     * @return the column label
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public String getColumnLabel(int column) throws SQLException {
        try {
            debugCodeCall("getColumnLabel", column);
            checkColumnIndex(column);
            return result.getAlias(--column);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Returns the column name.
     *
     * @param column the column index (1,2,...)
     * @return the column name
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public String getColumnName(int column) throws SQLException {
        try {
            debugCodeCall("getColumnName", column);
            checkColumnIndex(column);
            return result.getColumnName(--column);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Returns the data type of a column.
     * See also java.sql.Type.
     *
     * @param column the column index (1,2,...)
     * @return the data type
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public int getColumnType(int column) throws SQLException {
        try {
            debugCodeCall("getColumnType", column);
            checkColumnIndex(column);
            int type = result.getColumnType(--column);
            return DataType.convertTypeToSQLType(type);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Returns the data type name of a column.
     *
     * @param column the column index (1,2,...)
     * @return the data type name
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public String getColumnTypeName(int column) throws SQLException {
        try {
            debugCodeCall("getColumnTypeName", column);
            checkColumnIndex(column);
            int type = result.getColumnType(--column);
            return DataType.getDataType(type).name;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Returns the schema name.
     *
     * @param column the column index (1,2,...)
     * @return the schema name, or "" (an empty string) if not applicable
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public String getSchemaName(int column) throws SQLException {
        try {
            debugCodeCall("getSchemaName", column);
            checkColumnIndex(column);
            String schema = result.getSchemaName(--column);
            return schema == null ? "" : schema;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Returns the table name.
     *
     * @param column the column index (1,2,...)
     * @return the table name
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public String getTableName(int column) throws SQLException {
        try {
            debugCodeCall("getTableName", column);
            checkColumnIndex(column);
            String table = result.getTableName(--column);
            return table == null ? "" : table;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Returns the catalog name.
     *
     * @param column the column index (1,2,...)
     * @return the catalog name
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public String getCatalogName(int column) throws SQLException {
        try {
            debugCodeCall("getCatalogName", column);
            checkColumnIndex(column);
            return catalog == null ? "" : catalog;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks if this an autoincrement column.
     * It always returns false.
     *
     * @param column the column index (1,2,...)
     * @return false
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        try {
            debugCodeCall("isAutoIncrement", column);
            checkColumnIndex(column);
            return result.isAutoIncrement(--column);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks if this column is case sensitive.
     * It always returns true.
     *
     * @param column the column index (1,2,...)
     * @return true
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        try {
            debugCodeCall("isCaseSensitive", column);
            checkColumnIndex(column);
            return true;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks if this column is searchable.
     * It always returns true.
     *
     * @param column the column index (1,2,...)
     * @return true
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isSearchable(int column) throws SQLException {
        try {
            debugCodeCall("isSearchable", column);
            checkColumnIndex(column);
            return true;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks if this is a currency column.
     * It always returns false.
     *
     * @param column the column index (1,2,...)
     * @return false
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isCurrency(int column) throws SQLException {
        try {
            debugCodeCall("isCurrency", column);
            checkColumnIndex(column);
            return false;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks if this is nullable column. Returns
     * ResultSetMetaData.columnNullableUnknown if this is not a column of a
     * table. Otherwise, it returns ResultSetMetaData.columnNoNulls if the
     * column is not nullable, and ResultSetMetaData.columnNullable if it is
     * nullable.
     *
     * @param column the column index (1,2,...)
     * @return ResultSetMetaData.column*
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public int isNullable(int column) throws SQLException {
        try {
            debugCodeCall("isNullable", column);
            checkColumnIndex(column);
            return result.getNullable(--column);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks if this column is signed.
     * It always returns true.
     *
     * @param column the column index (1,2,...)
     * @return true
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isSigned(int column) throws SQLException {
        try {
            debugCodeCall("isSigned", column);
            checkColumnIndex(column);
            return true;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks if this column is read only.
     * It always returns false.
     *
     * @param column the column index (1,2,...)
     * @return false
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isReadOnly(int column) throws SQLException {
        try {
            debugCodeCall("isReadOnly", column);
            checkColumnIndex(column);
            return false;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks whether it is possible for a write on this column to succeed.
     * It always returns true.
     *
     * @param column the column index (1,2,...)
     * @return true
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isWritable(int column) throws SQLException {
        try {
            debugCodeCall("isWritable", column);
            checkColumnIndex(column);
            return true;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Checks whether a write on this column will definitely succeed.
     * It always returns false.
     *
     * @param column the column index (1,2,...)
     * @return false
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        try {
            debugCodeCall("isDefinitelyWritable", column);
            checkColumnIndex(column);
            return false;
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Gets the Java class name of the object that will be returned
     * if ResultSet.getObject is called.
     *
     * @param column the column index (1,2,...)
     * @return the Java class name
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public String getColumnClassName(int column) throws SQLException {
        try {
            debugCodeCall("getColumnClassName", column);
            checkColumnIndex(column);
            int type = result.getColumnType(--column);
            return DataType.getTypeClassName(type);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Gets the precision for this column.
     * This method always returns 0.
     *
     * @param column the column index (1,2,...)
     * @return the precision
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public int getPrecision(int column) throws SQLException {
        try {
            debugCodeCall("getPrecision", column);
            checkColumnIndex(column);
            long prec = result.getColumnPrecision(--column);
            return MathUtils.convertLongToInt(prec);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Gets the scale for this column.
     * This method always returns 0.
     *
     * @param column the column index (1,2,...)
     * @return the scale
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public int getScale(int column) throws SQLException {
        try {
            debugCodeCall("getScale", column);
            checkColumnIndex(column);
            return result.getColumnScale(--column);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    /**
     * Gets the maximum display size for this column.
     *
     * @param column the column index (1,2,...)
     * @return the display size
     * @throws SQLException if the result set is closed or invalid
     */
    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        try {
            debugCodeCall("getColumnDisplaySize", column);
            checkColumnIndex(column);
            return result.getDisplaySize(--column);
        } catch (Exception e) {
            throw logAndConvert(e);
        }
    }

    private void checkClosed() {
        if (rs != null) {
            rs.checkClosed();
        }
        if (prep != null) {
            prep.checkClosed();
        }
    }

    private void checkColumnIndex(int columnIndex) {
        checkClosed();
        if (columnIndex < 1 || columnIndex > columnCount) {
            throw DbException.getInvalidValueException("columnIndex", columnIndex);
        }
    }

    /**
     * INTERNAL
     */
    @Override
    public String toString() {
        return getTraceObjectName() + ": columns=" + columnCount;
    }

}
