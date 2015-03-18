package org.mdb.app;

import spark.Spark;

public class SparkApp {
    public static void main(String[] args) {
        //http://localhost:4567/hello
        Spark.get("/hello",(req,res) -> "Hi Spark ... !!!");
    }
}
