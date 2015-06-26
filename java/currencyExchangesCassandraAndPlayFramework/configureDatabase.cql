CREATE KEYSPACE catalogtest   WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

-- We want to be able to efficiently sort by day
-- while having direct access to cname:
-- 
-- Therefore, our partition key will be currency's name
-- and our clustering key will be day

CREATE TABLE catalogtest.currencies (
  key blob, -- this is the partition key
  column1 blob, --this is the clustering key
  value blob, --this is the payload (the currency's exchange value)
  PRIMARY KEY ((key), column1)
) WITH COMPACT STORAGE ;

-- In this way, we will be able to acces quickly to ranges of dates for a given currency name (key column)


----
----This table is for testing
----
CREATE TABLE catalogtest.testcurrencies (
  key blob, -- this is the partition key
  column1 blob, --this is the clustering key
  value blob, --this is the payload (the currency's exchange value)
  PRIMARY KEY ((key), column1)
) WITH COMPACT STORAGE ;


