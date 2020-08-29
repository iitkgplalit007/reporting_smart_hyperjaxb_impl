USE [NCCI_WCSTAT_STAGING]
CREATE TABLE [WCSTREF].[ncci_state](
	[PRIORITY] [int] NULL,
	[TYPECODE] [varchar](50) NOT NULL,
	[RETIRED] [bit] NOT NULL,
	[NAME] [varchar](256) NOT NULL,
	[ID] [numeric](19, 0) NOT NULL,
	[DESCRIPTION] [varchar](512) NULL,
	[NCCI_STATE_CODE] [varchar](10) NULL,
 CONSTRAINT [state_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));

CREATE TABLE [WCSTREF].[nccireport](
	[PRIORITY] [int] NULL,
	[TYPECODE] [varchar](50) NOT NULL,
	[RETIRED] [bit] NOT NULL,
	[NAME] [varchar](256) NOT NULL,
	[ID] [numeric](19, 0) NOT NULL,
	[DESCRIPTION] [varchar](512) NULL,
	--[NCCI_STATE_CODE] [varchar](10) NULL,
 CONSTRAINT [report_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));

CREATE TABLE [WCSTREF].[nccistatereport](
	[ID] [numeric](19, 0) NOT NULL,
    [State] [numeric](19, 0) NULL,
    [Report] [numeric](19, 0) NULL,
    [NCCISubjectHeaderName] [numeric](19, 0) NOT NULL,
 CONSTRAINT [nccistatereport_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));


CREATE TABLE [WCSTREF].[nccisubjectheadertype](
	[PRIORITY] [int] NULL,
	[TYPECODE] [varchar](50) NOT NULL,
	[RETIRED] [bit] NOT NULL,
	[NAME] [varchar](256) NOT NULL,
	[ID] [numeric](19, 0) NOT NULL,
	[DESCRIPTION] [varchar](512) NULL,
	[RECORD_TYPE] [varchar](10) NULL,
	[FILE_RECORD_TYPE] [numeric](19, 0) NULL,
	[RECORD_NUMBER_LENGTH] [int] NULL,
	[RECORD_NUMBER_START_POSITION] [int] NULL,
	[TargetSchema] [varchar](20) NULL,
	[TargetTable] [varchar](20) NULL,
 CONSTRAINT [nccisubjectheadertype_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));
CREATE TABLE [WCSTREF].[nccisubjectheader](
	[ElementLength] [int] NULL,
	[CreateUserID] [varchar](max) NOT NULL,
	[FileEndPosition] [int] NULL,
	[Retired] [bigint] NOT NULL,
	[CreateTime] [datetime2](7) NOT NULL,
	[FileStartPosition] [int] NULL,
	[UpdateUserID] [varchar](max) NOT NULL,
	[UpdateTime] [datetime2](7) NOT NULL,
	[ID] [numeric](19, 0) NOT NULL,
	[NCCIElementDetailsID] [numeric](19, 0) NOT NULL,
	[NCCISubjectHeaderName] [numeric](19, 0) NOT NULL,
	[CLASS][varchar](2) NOT NULL,
 CONSTRAINT [nccisubjectheader_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));
CREATE TABLE [WCSTREF].[nccistatetosubjectheader](
	[State] [numeric](19, 0) NULL,
	[CreateUserID] [varchar](max) NOT NULL,
	[UpdateTime] [datetime2](7) NOT NULL,
	[CreateTime] [datetime2](7) NOT NULL,
	[Retired] [bigint] NOT NULL,
	[ID] [numeric](19, 0) NOT NULL,
	[NCCISubjectHeader] [numeric](19, 0) NOT NULL,
	[UpdateUserID] [varchar](max) NOT NULL,
 CONSTRAINT [nccistatetosubjectheader_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));
CREATE TABLE [WCSTREF].[nccielementdetails](
	[CreateUserID] [varchar](max) NOT NULL,
	[WCSTATHandbookName] [varchar](max) NULL,
	[DataType] [int] NULL,
	[TypeName] [varchar](20) NULL,
	[SchemaElementName] [varchar](max) NOT NULL,
	[Length] [int] NULL,
	[Retired] [bigint] NOT NULL,
	[CreateTime] [datetime2](7) NOT NULL,
	[Scale] [varchar](20) NULL,
	[UpdateUserID] [varchar](max) NOT NULL,
	[UpdateTime] [datetime2](7) NOT NULL,
	[ID] [numeric](19, 0) NOT NULL,
	[Precision] [int] NULL,
 CONSTRAINT [nccielementdetails_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));

CREATE TABLE [WCSTREF].[nccifilerecordtype](
	[ID] [numeric](19, 0) NOT NULL,
	[HEADER_TYPE] [varchar](20) NULL
 CONSTRAINT [nccifilerecordtype_PK] PRIMARY KEY CLUSTERED
(
	[ID] ASC
));
ALTER TABLE [wcstref].[nccistatetosubjectheader]  WITH CHECK ADD FOREIGN KEY([NCCISubjectHeader])
REFERENCES [wcstref].[nccisubjectheader] ([ID]);
ALTER TABLE [wcstref].[nccisubjectheader]  WITH CHECK ADD FOREIGN KEY([NCCIElementDetailsID])
REFERENCES [wcstref].[nccielementdetails] ([ID]);
ALTER TABLE [wcstref].[nccistatetosubjectheader]  WITH CHECK ADD FOREIGN KEY([State])
REFERENCES [wcstref].[ncci_state] ([ID]);
ALTER TABLE [wcstref].[nccisubjectheader]  WITH CHECK ADD FOREIGN KEY([NCCISubjectHeaderName])
REFERENCES [wcstref].[nccisubjectheadertype] ([ID]);
ALTER TABLE [wcstref].[nccistatereport]  WITH CHECK ADD FOREIGN KEY([STATE])
REFERENCES [wcstref].[nccisubjectheadertype] ([ID]);
ALTER TABLE [wcstref].[nccistatereport]  WITH CHECK ADD FOREIGN KEY([Report])
REFERENCES [wcstref].[nccireport] ([ID]);
ALTER TABLE [wcstref].[nccistatereport]  WITH CHECK ADD FOREIGN KEY([NCCISubjectHeaderName])
REFERENCES [wcstref].[nccisubjectheadertype] ([ID]);
ALTER TABLE [wcstref].[nccisubjectheadertype]  WITH CHECK ADD FOREIGN KEY([FILE_RECORD_TYPE])
REFERENCES [wcstref].[nccifilerecordtype] ([ID]);


CREATE TABLE [wcstref].[ncciunitstatreportschedule](
	[ID] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[DATE_OF_EVALUATION_ITEM] [int] NULL,
	[DATE_OF_REPORTING_ITEM] [int] NULL,
	[REPORT_LEVEL_ITEM] [varchar](255) NULL,
	[REPORT_NUMBER_ITEM] [varchar](255) NULL,
PRIMARY KEY CLUSTERED
(
	[ID] ASC
));