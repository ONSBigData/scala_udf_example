# Scala UDF Example

Example of a UDF defined in Scala, callable from PySpark.

Simply wraps a call to JaroWinklerDistance from Apache commons.

## Usage

To build the Jar:

    mvn package
    
To add the jar to PySpark set the following config:

    spark.driver.extraClassPath /path/to/jarfile.jar
    spark.jars /path/to/jarfile.jar
    
To register the function with PySpark:

```python
sqlContext = SQLContext(spark.sparkContext)
sqlContext.registerJavaFunction('jaro_winkler', 'uk.gov.ons.mdr.examples.JaroWinklerDistance', pyspark.sql.types.DoubleType())
```