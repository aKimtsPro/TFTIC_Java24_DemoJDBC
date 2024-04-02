CREATE TABLE section (
  section_id BIGSERIAL NOT NULL,
  section_name varchar(50),
  delegate_id bigint,
  CONSTRAINT PK_section PRIMARY KEY (section_id)
);

CREATE TABLE professor (
  professor_id BIGSERIAL NOT NULL,
  professor_name varchar(30) NOT NULL,
  professor_surname varchar(30) NOT NULL,
  section_id bigint NOT NULL,
  professor_office bigint NOT NULL,
  professor_email varchar(30) NOT NULL,
  professor_hire_date date NOT NULL,
  professor_wage bigint NOT NULL,
  CONSTRAINT PK_professor PRIMARY KEY (professor_id),
  constraint FK_professor_section foreign key (section_id) references section (section_id)
);

CREATE TABLE course (
  course_id varchar(8) NOT NULL,
  course_name varchar(200) NOT NULL ,
  course_ects decimal(3,1) NOT NULL,
  professor_id bigint NOT NULL,
  CONSTRAINT PK_course PRIMARY KEY (course_id),
  constraint FK_course_professor foreign key (professor_id) references professor (professor_id)
);

CREATE TABLE student (
  student_id BIGSERIAL NOT NULL,
  first_name varchar(50),
  last_name varchar(50),
  birth_date timestamp,
  login varchar(50),
  section_id bigint,
  year_result bigint,
  course_id varchar(6) NOT NULL,
  CONSTRAINT PK_student PRIMARY KEY (student_id),
  constraint FK_student_section foreign key (section_id) references section (section_id)
);

CREATE TABLE grade (
  grade char(2) NOT NULL,
  lower_bound bigint NOT NULL,
  upper_bound bigint NOT NULL,
  CONSTRAINT PK_grade PRIMARY KEY (grade)
);
