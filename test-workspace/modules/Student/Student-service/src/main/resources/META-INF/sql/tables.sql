create table stud_Student (
	studentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createdby LONG,
	modifiedby VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	enrollmentNo VARCHAR(75) null,
	firstName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	contactNo VARCHAR(75) null,
	city VARCHAR(75) null
);