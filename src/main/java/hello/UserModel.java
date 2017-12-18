package hello;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;

public class UserModel {

    private String username;
    private String password; 
    private String dbpw = "";
    private ArrayList<String> accountTypes = new ArrayList<String>();
    private ArrayList<String> accountAmounts = new ArrayList<String>();

    public UserModel(){
    }

    // public Greeting(long id, String content) {
    //     this.id = id;
    //     this.content = content;
    // }

    public UserModel(String username){
        this.username = username;
        this.password = "";
    }

    public UserModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return username;
    }

    public String getUsername(){
        return username;
    }

    public ArrayList<String> getAccountTypes(){
        return accountTypes;
    }

    public ArrayList<String> getAccountAmounts(){
        return accountAmounts;
    }

    public Boolean validateUser(){
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase database = mongoClient.getDatabase("test_db");
    
        MongoCollection<Document> collection = database.getCollection("users");
        collection.find(eq("username", this.username)).forEach(validatePrintBlock);
        
        Boolean match = this.dbpw.equals(this.password);
        this.dbpw = "";

        return match;
    }

    public void getUserAccounts(){
        MongoClient mongoClient = new MongoClient("localhost",27017);
        MongoDatabase database = mongoClient.getDatabase("test_db");
        MongoCollection<Document> collection = database.getCollection("users");
        collection.find(eq("username", this.username)).forEach(getAccountsPrintBlock);
    }

    public Block<Document> validatePrintBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			// parse result obtained from collection

			String username = (String) document.get("username");
			String password = (String) document.get("password");
			dbpw = password;
		}
	};

    public Block<Document> getAccountsPrintBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			// parse result obtained from collection

			Collection<Document> accounts = (Collection) document.get("accounts");
            for (int i=0; i<accounts.size(); i++){
                ArrayList accountsArray = new ArrayList<Document>(accounts);
                Document accountDoc = (Document) accountsArray.get(i);
                String type = (String) accountDoc.get("type");
                String amount = (String) accountDoc.get("amount");
                accountTypes.add(type);
                accountAmounts.add(amount);
            }
		}
	};
}
