/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.ignek.student.service.persistence.test;

import com.ignek.student.exception.NoSuchStudentException;
import com.ignek.student.model.Student;
import com.ignek.student.service.StudentLocalServiceUtil;
import com.ignek.student.service.persistence.StudentPersistence;
import com.ignek.student.service.persistence.StudentUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class StudentPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.ignek.student.service"));

	@Before
	public void setUp() {
		_persistence = StudentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Student> iterator = _students.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Student student = _persistence.create(pk);

		Assert.assertNotNull(student);

		Assert.assertEquals(student.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Student newStudent = addStudent();

		_persistence.remove(newStudent);

		Student existingStudent = _persistence.fetchByPrimaryKey(
			newStudent.getPrimaryKey());

		Assert.assertNull(existingStudent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addStudent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Student newStudent = _persistence.create(pk);

		newStudent.setGroupId(RandomTestUtil.nextLong());

		newStudent.setCompanyId(RandomTestUtil.nextLong());

		newStudent.setCreatedby(RandomTestUtil.nextLong());

		newStudent.setModifiedby(RandomTestUtil.randomString());

		newStudent.setCreateDate(RandomTestUtil.nextDate());

		newStudent.setModifiedDate(RandomTestUtil.nextDate());

		newStudent.setEnrollmentNo(RandomTestUtil.randomString());

		newStudent.setFirstName(RandomTestUtil.randomString());

		newStudent.setLastName(RandomTestUtil.randomString());

		newStudent.setContactNo(RandomTestUtil.randomString());

		newStudent.setCity(RandomTestUtil.randomString());

		_students.add(_persistence.update(newStudent));

		Student existingStudent = _persistence.findByPrimaryKey(
			newStudent.getPrimaryKey());

		Assert.assertEquals(
			existingStudent.getStudentId(), newStudent.getStudentId());
		Assert.assertEquals(
			existingStudent.getGroupId(), newStudent.getGroupId());
		Assert.assertEquals(
			existingStudent.getCompanyId(), newStudent.getCompanyId());
		Assert.assertEquals(
			existingStudent.getCreatedby(), newStudent.getCreatedby());
		Assert.assertEquals(
			existingStudent.getModifiedby(), newStudent.getModifiedby());
		Assert.assertEquals(
			Time.getShortTimestamp(existingStudent.getCreateDate()),
			Time.getShortTimestamp(newStudent.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingStudent.getModifiedDate()),
			Time.getShortTimestamp(newStudent.getModifiedDate()));
		Assert.assertEquals(
			existingStudent.getEnrollmentNo(), newStudent.getEnrollmentNo());
		Assert.assertEquals(
			existingStudent.getFirstName(), newStudent.getFirstName());
		Assert.assertEquals(
			existingStudent.getLastName(), newStudent.getLastName());
		Assert.assertEquals(
			existingStudent.getContactNo(), newStudent.getContactNo());
		Assert.assertEquals(existingStudent.getCity(), newStudent.getCity());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Student newStudent = addStudent();

		Student existingStudent = _persistence.findByPrimaryKey(
			newStudent.getPrimaryKey());

		Assert.assertEquals(existingStudent, newStudent);
	}

	@Test(expected = NoSuchStudentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Student> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"stud_Student", "studentId", true, "groupId", true, "companyId",
			true, "createdby", true, "modifiedby", true, "createDate", true,
			"modifiedDate", true, "enrollmentNo", true, "firstName", true,
			"lastName", true, "contactNo", true, "city", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Student newStudent = addStudent();

		Student existingStudent = _persistence.fetchByPrimaryKey(
			newStudent.getPrimaryKey());

		Assert.assertEquals(existingStudent, newStudent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Student missingStudent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingStudent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Student newStudent1 = addStudent();
		Student newStudent2 = addStudent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStudent1.getPrimaryKey());
		primaryKeys.add(newStudent2.getPrimaryKey());

		Map<Serializable, Student> students = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, students.size());
		Assert.assertEquals(
			newStudent1, students.get(newStudent1.getPrimaryKey()));
		Assert.assertEquals(
			newStudent2, students.get(newStudent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Student> students = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(students.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Student newStudent = addStudent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStudent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Student> students = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, students.size());
		Assert.assertEquals(
			newStudent, students.get(newStudent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Student> students = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(students.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Student newStudent = addStudent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStudent.getPrimaryKey());

		Map<Serializable, Student> students = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, students.size());
		Assert.assertEquals(
			newStudent, students.get(newStudent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			StudentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Student>() {

				@Override
				public void performAction(Student student) {
					Assert.assertNotNull(student);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Student newStudent = addStudent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Student.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("studentId", newStudent.getStudentId()));

		List<Student> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Student existingStudent = result.get(0);

		Assert.assertEquals(existingStudent, newStudent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Student.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("studentId", RandomTestUtil.nextLong()));

		List<Student> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Student newStudent = addStudent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Student.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("studentId"));

		Object newStudentId = newStudent.getStudentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"studentId", new Object[] {newStudentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStudentId = result.get(0);

		Assert.assertEquals(existingStudentId, newStudentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Student.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("studentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"studentId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Student addStudent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Student student = _persistence.create(pk);

		student.setGroupId(RandomTestUtil.nextLong());

		student.setCompanyId(RandomTestUtil.nextLong());

		student.setCreatedby(RandomTestUtil.nextLong());

		student.setModifiedby(RandomTestUtil.randomString());

		student.setCreateDate(RandomTestUtil.nextDate());

		student.setModifiedDate(RandomTestUtil.nextDate());

		student.setEnrollmentNo(RandomTestUtil.randomString());

		student.setFirstName(RandomTestUtil.randomString());

		student.setLastName(RandomTestUtil.randomString());

		student.setContactNo(RandomTestUtil.randomString());

		student.setCity(RandomTestUtil.randomString());

		_students.add(_persistence.update(student));

		return student;
	}

	private List<Student> _students = new ArrayList<Student>();
	private StudentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}