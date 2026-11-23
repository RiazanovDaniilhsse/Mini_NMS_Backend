ALTER TABLE threshold_rules RENAME COLUMN threshold_value TO numeric_value;
ALTER TABLE threshold_rules ADD COLUMN boolean_value BOOLEAN;
ALTER TABLE threshold_rules ADD COLUMN string_values TEXT;