-- Create the database
IF EXISTS(SELECT * FROM sys.databases WHERE name = 'NCCI_WCSTAT_DATA')
   BEGIN
		PRINT 'Dropping the NCCI_WCSTAT_STAGING database'
		DROP DATABASE NCCI_WCSTAT_DATA
   END
		PRINT 'Creating the NCCI_WCSTAT_STAGING database'
		Create DATABASE NCCI_WCSTAT_DATA
