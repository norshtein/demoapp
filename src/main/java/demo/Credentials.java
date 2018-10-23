package demo;

public class Credentials {
    private String uri;
    private String primaryConnectionString;
    private String primaryKey;
    private String databaseName;
    private String documentdb_database_id;
    private String documentdb_host_endpoint;
    private String documentdb_master_key;

    public Credentials(){}

    public Credentials(String uri, String primaryConnectionString, String primaryKey, String databaseName, String documentdb_database_id, String documentdb_host_endpoint, String documentdb_master_key) {
        this.uri = uri;
        this.primaryConnectionString = primaryConnectionString;
        this.primaryKey = primaryKey;
        this.databaseName = databaseName;
        this.documentdb_database_id = documentdb_database_id;
        this.documentdb_host_endpoint = documentdb_host_endpoint;
        this.documentdb_master_key = documentdb_master_key;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPrimaryConnectionString() {
        return primaryConnectionString;
    }

    public void setPrimaryConnectionString(String primaryConnectionString) {
        this.primaryConnectionString = primaryConnectionString;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDocumentdb_database_id() {
        return documentdb_database_id;
    }

    public void setDocumentdb_database_id(String documentdb_database_id) {
        this.documentdb_database_id = documentdb_database_id;
    }

    public String getDocumentdb_host_endpoint() {
        return documentdb_host_endpoint;
    }

    public void setDocumentdb_host_endpoint(String documentdb_host_endpoint) {
        this.documentdb_host_endpoint = documentdb_host_endpoint;
    }

    public String getDocumentdb_master_key() {
        return documentdb_master_key;
    }

    public void setDocumentdb_master_key(String documentdb_master_key) {
        this.documentdb_master_key = documentdb_master_key;
    }
}