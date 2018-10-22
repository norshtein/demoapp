package demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.azure.documentdb.*;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDAO {
    private static DocumentClient client;
    private static String databaseName;
    private static String collectionName;

    static{
        Credentials credentials = getCredentials();
        if(credentials != null){
            createClient(credentials);
            databaseName = "tosidemodb";
            collectionName = "UserCollection";
        } else {
            client = null;
        }
    }

    // service name hard-coded for demo
    private static Credentials getCredentials(){
        Map<String,String> env = System.getenv();
        String servicesInJSON = env.get("VCAP_SERVICES");
        JSONObject obj = JSON.parseObject(servicesInJSON);
        JSONArray services;
        if(obj.containsKey("azure-cosmosdb-sql-account")){
            services = obj.getJSONArray("azure-cosmosdb-sql-account");
        } else if (obj.containsKey("azure-cosmosdb-sql-account-registered")) {
            services = obj.getJSONArray("azure-cosmosdb-sql-account-registered");
        } else {
            return null;
        }

        JSONObject credentialsJSONObject = services.getJSONObject(0).getJSONObject("credentials");
        Credentials credentials = new Credentials();
        credentials.setUri(credentialsJSONObject.getString("uri"));
        credentials.setPrimaryKey(credentialsJSONObject.getString("primaryKey"));

        return credentials;
    }

    private static void createClient(Credentials credentials){
        ConnectionPolicy policy = new ConnectionPolicy();
        policy.setEnableEndpointDiscovery(true);

        client = new DocumentClient(
                credentials.getUri(),
                credentials.getPrimaryKey(),
                policy,
                ConsistencyLevel.Session
        );
    }


    public static List<User> getUsers()throws DocumentClientException, IOException{
        createDatabaseIfNotExists(databaseName);
        createDocumentCollectionIfNotExists(databaseName, collectionName);
        String collectionLink = String.format("/dbs/%s/colls/%s", databaseName, collectionName);

        FeedResponse<Document> queryResults = client.queryDocuments(
                collectionLink,
                "SELECT * FROM User",
                null
        );
        ArrayList<User> users = new ArrayList<User>();
        for (Document doc: queryResults.getQueryIterable()){
            User user = new User();
            user.setId(doc.getString("id"));
            user.setFirstName(doc.getString("firstName"));
            user.setLastName(doc.getString("lastName"));
            users.add(user);
        }
        return users;
    }

    public static boolean addUser(User user) throws DocumentClientException, IOException{
        createUserDocumentIfNotExists(databaseName, collectionName, user);
        return true;
    }

    private static void createDatabaseIfNotExists(String databaseName) throws DocumentClientException, IOException {
        String databaseLink = String.format("/dbs/%s", databaseName);

        // Check to verify a database with the id=FamilyDB does not exist
        try {
            client.readDatabase(databaseLink, null);
        } catch (DocumentClientException de) {
            // If the database does not exist, create a new database
            if (de.getStatusCode() == 404) {
                Database database = new Database();
                database.setId(databaseName);

                client.createDatabase(database, new RequestOptions());
            } else {
                throw de;
            }
        }
    }

    private static void createDocumentCollectionIfNotExists(String databaseName, String collectionName) throws IOException,
            DocumentClientException {
        String databaseLink = String.format("/dbs/%s", databaseName);
        String collectionLink = String.format("/dbs/%s/colls/%s", databaseName, collectionName);

        try {
            client.readCollection(collectionLink, null);
        } catch (DocumentClientException de) {
            // If the document collection does not exist, create a new
            // collection
            if (de.getStatusCode() == 404) {
                DocumentCollection collectionInfo = new DocumentCollection();
                collectionInfo.setId(collectionName);

                // Optionally, you can configure the indexing policy of a
                // collection. Here we configure collections for maximum query
                // flexibility including string range queries.
                RangeIndex index = new RangeIndex(DataType.String);
                index.setPrecision(-1);

                collectionInfo.setIndexingPolicy(new IndexingPolicy(new Index[] { index }));

                // DocumentDB collections can be reserved with throughput
                // specified in request units/second. 1 RU is a normalized
                // request equivalent to the read of a 1KB document. Here we
                // create a collection with 400 RU/s.
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.setOfferThroughput(400);

                client.createCollection(databaseLink, collectionInfo, requestOptions);
            } else {
                throw de;
            }
        }
    }

    private static void createUserDocumentIfNotExists(String databaseName, String collectionName, User user)
            throws DocumentClientException, IOException {
        try {
            String documentLink = String.format("/dbs/%s/colls/%s/docs/%s", databaseName, collectionName, user.getId());
            client.readDocument(documentLink, new RequestOptions());
        } catch (DocumentClientException de) {
            if (de.getStatusCode() == 404) {
                String collectionLink = String.format("/dbs/%s/colls/%s", databaseName, collectionName);
                client.createDocument(collectionLink, user, new RequestOptions(), true);
            } else {
                throw de;
            }
        }
    }
}

