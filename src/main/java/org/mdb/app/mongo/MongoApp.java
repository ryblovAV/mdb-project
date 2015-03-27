package org.mdb.app.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.bson.*;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.io.StringWriter;
import java.util.Date;
import java.util.Arrays;


public class MongoApp {

    static public void printJson(Document doc) {
        JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL,true));
        new DocumentCodec().encode(jsonWriter,doc, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
        System.out.println(jsonWriter.getWriter());
        System.out.flush();
    }

    static public Document createDoc(String str) {
        return new Document().
                append("str", str).
                append("objId", new ObjectId()).
                append("null", null).
                append("bool", true).
                append("date", new Date()).
                append("doc", new Document("x", 0)).
                append("array",Arrays.asList(1,2,3));
    }

//    static public BsonDocument createBSONDoc() {
//        return new BsonDocument().
//                append("str", new BsonString("StringValue")).
//                append("objId", new BsonObjectId()).
//                append("bool", new BsonBoolean(true)).
//                append("date", new BsonDateTime());
//    }


    static public void run() {
        //100 connections pull
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        //height weight object
        // represent pull of socket or connections (single server)
        MongoClient client = new MongoClient(new ServerAddress(), options);

        //light weight object
        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collections = db.getCollection("test2", Document.class);

        collections.drop();
        collections.insertMany(Arrays.asList(createDoc("doc1"),createDoc("doc2")));

    }

    public static void main(String[] args) {
        run();

//        printJson(createDoc());

    }

}
