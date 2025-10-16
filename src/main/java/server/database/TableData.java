package server.database;

import server.data.Example;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * classe che permette l'inserimento dei dati nella tabella del database
 */
public class TableData {

    private DbAccess db;
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * metodo che inserisce i dati nella tabella del database
     * @param table
     * @return
     * @throws SQLException
     * @throws EmptySetException
     * @throws MissingNumberException
     * @throws DatabaseConnectionException
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, MissingNumberException, DatabaseConnectionException {
        List<Example> examples = new ArrayList<>();
        Connection connection = db.getConnection();
        Statement statement = null;
        ResultSet resultset = null;
        TableSchema schema = new TableSchema(db, table);

        statement = connection.createStatement();
        String query = "SELECT DISTINCT * FROM " + table;
        resultset = statement.executeQuery(query);

        if (!resultset.isBeforeFirst()) { // Verifica se il ResultSet è vuoto
            throw new EmptySetException("La tabella " + table + " è vuota.\n");
        }

        while (resultset.next()) {
            Example example = new Example();
            for (int i = 0; i < schema.getNumberOfAttributes(); i++) {
                TableSchema.Column column = schema.getColumn(i);
                if (!column.isNumber()) {
                    throw new MissingNumberException("Attributo non numerico trovato: " + column.getColumnName() + "\n");
                }
                example.add(resultset.getDouble(column.getColumnName()));
            }
            examples.add(example);
        }

        resultset.close();
        statement.close();
        db.closeConnection();

        return examples;
    }
}

