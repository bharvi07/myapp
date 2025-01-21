/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.ignek.student.model.impl;

import com.ignek.student.model.Student;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Student in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class StudentCacheModel implements CacheModel<Student>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StudentCacheModel)) {
			return false;
		}

		StudentCacheModel studentCacheModel = (StudentCacheModel)object;

		if (studentId == studentCacheModel.studentId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, studentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{studentId=");
		sb.append(studentId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createdby=");
		sb.append(createdby);
		sb.append(", modifiedby=");
		sb.append(modifiedby);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", enrollmentNo=");
		sb.append(enrollmentNo);
		sb.append(", firstName=");
		sb.append(firstName);
		sb.append(", lastName=");
		sb.append(lastName);
		sb.append(", contactNo=");
		sb.append(contactNo);
		sb.append(", city=");
		sb.append(city);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Student toEntityModel() {
		StudentImpl studentImpl = new StudentImpl();

		studentImpl.setStudentId(studentId);
		studentImpl.setGroupId(groupId);
		studentImpl.setCompanyId(companyId);
		studentImpl.setCreatedby(createdby);

		if (modifiedby == null) {
			studentImpl.setModifiedby("");
		}
		else {
			studentImpl.setModifiedby(modifiedby);
		}

		if (createDate == Long.MIN_VALUE) {
			studentImpl.setCreateDate(null);
		}
		else {
			studentImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			studentImpl.setModifiedDate(null);
		}
		else {
			studentImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (enrollmentNo == null) {
			studentImpl.setEnrollmentNo("");
		}
		else {
			studentImpl.setEnrollmentNo(enrollmentNo);
		}

		if (firstName == null) {
			studentImpl.setFirstName("");
		}
		else {
			studentImpl.setFirstName(firstName);
		}

		if (lastName == null) {
			studentImpl.setLastName("");
		}
		else {
			studentImpl.setLastName(lastName);
		}

		if (contactNo == null) {
			studentImpl.setContactNo("");
		}
		else {
			studentImpl.setContactNo(contactNo);
		}

		if (city == null) {
			studentImpl.setCity("");
		}
		else {
			studentImpl.setCity(city);
		}

		studentImpl.resetOriginalValues();

		return studentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		studentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		createdby = objectInput.readLong();
		modifiedby = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		enrollmentNo = objectInput.readUTF();
		firstName = objectInput.readUTF();
		lastName = objectInput.readUTF();
		contactNo = objectInput.readUTF();
		city = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(studentId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(createdby);

		if (modifiedby == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(modifiedby);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (enrollmentNo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(enrollmentNo);
		}

		if (firstName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(firstName);
		}

		if (lastName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(lastName);
		}

		if (contactNo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(contactNo);
		}

		if (city == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(city);
		}
	}

	public long studentId;
	public long groupId;
	public long companyId;
	public long createdby;
	public String modifiedby;
	public long createDate;
	public long modifiedDate;
	public String enrollmentNo;
	public String firstName;
	public String lastName;
	public String contactNo;
	public String city;

}