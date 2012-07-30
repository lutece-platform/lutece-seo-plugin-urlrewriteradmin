--
-- Structure for table urlrewriteradmin
--

DROP TABLE IF EXISTS urlrewriteradmin_rule;
CREATE TABLE urlrewriteradmin_rule (		
  id_rule INT DEFAULT '0' NOT NULL,
  rule_from VARCHAR(255) DEFAULT '' NOT NULL,
  rule_to VARCHAR(255) DEFAULT '' NOT NULL 
);