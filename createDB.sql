-------- Db "QuizCompany" 
--------------------------------------------------------------------------------------------------------------
create table company
(
    companyid smallserial not null
        constraint "Company_pkey"
            primary key,
    name      char(250)   not null
);

alter table company
    owner to postgres;

INSERT INTO public.company (companyid, name) VALUES (6, 'My company1                                                                                                                                                                                                                                               ');

------------------------------------------------------------------------------------------------------------

create table salaries
(
    salaryid smallserial not null
        constraint salaries_pkey
            primary key,
    name     char(50)    not null
);

alter table salaries
    owner to postgres;

INSERT INTO public.salaries (salaryid, name) VALUES (0, 'Оклад                                             ');
INSERT INTO public.salaries (salaryid, name) VALUES (1, 'Почасовая оплата                                  ');
INSERT INTO public.salaries (salaryid, name) VALUES (2, 'Оклад + проценты                                  ');

------------------------------------------------------------------------------------------------------------
create table workschedules
(
    scheduleid bigserial not null
        constraint workschedules_pkey
            primary key,
    name       char(50)  not null
);

alter table workschedules
    owner to postgres;

INSERT INTO public.workschedules (scheduleid, name) VALUES (-1, 'Начать работать раньше                            ');
INSERT INTO public.workschedules (scheduleid, name) VALUES (0, 'Остаться в старых рабочих условиях                ');
INSERT INTO public.workschedules (scheduleid, name) VALUES (1, 'Начать работать позже                             ');
INSERT INTO public.workschedules (scheduleid, name) VALUES (10, 'Работать из дому                                  ');

---------------------------------------------------------------------------------------------------------------------

create table departments
(
    departmentid  serial  not null
        constraint departments_pkey
            primary key,
    name          char(50),
    free_schedule boolean default false,
    sync_schedule boolean default false,
    companyid     integer not null
        constraint departments_company
            references company
);

alter table departments
    owner to postgres;

INSERT INTO public.departments (departmentid, name, free_schedule, sync_schedule, companyid) VALUES (1, 'Administration                                    ', false, true, 6);
INSERT INTO public.departments (departmentid, name, free_schedule, sync_schedule, companyid) VALUES (2, 'Sales                                             ', false, true, 6);
INSERT INTO public.departments (departmentid, name, free_schedule, sync_schedule, companyid) VALUES (3, 'Buhgalteria                                       ', false, false, 6);
INSERT INTO public.departments (departmentid, name, free_schedule, sync_schedule, companyid) VALUES (5, 'Tehnology                                         ', true, false, 6);

------------------------------------------------------------------------------------------------------------------

create table departments_schedule
(
    departmentid integer not null
        constraint departments_schedule_pkey
            primary key
        constraint departments_schedule_schedule
            references departments (departmentid),
    scheduleid   integer not null
        constraint departments_schedule_workschedules
            references workschedules,
    numberhours  integer default 0
);

alter table departments_schedule
    owner to postgres;

INSERT INTO public.departments_schedule (departmentid, scheduleid, numberhours) VALUES (1, -1, -1);
INSERT INTO public.departments_schedule (departmentid, scheduleid, numberhours) VALUES (2, 1, 1);

-----------------------------------------------------------------------------------------------------------------------

create table titles
(
    titleid       serial   not null
        constraint titles_pkey
            primary key,
    name          char(50) not null,
    free_schedule boolean default false
);

alter table titles
    owner to postgres;

INSERT INTO public.titles (titleid, name, free_schedule) VALUES (1, 'director                                          ', false);
INSERT INTO public.titles (titleid, name, free_schedule) VALUES (3, 'buhhgalter                                        ', false);
INSERT INTO public.titles (titleid, name, free_schedule) VALUES (2, 'programmer                                        ', true);
INSERT INTO public.titles (titleid, name, free_schedule) VALUES (4, 'sale agent                                        ', true);

-------------------------------------------------------------------------------------------------------------------------

create table employee
(
    employeeid   serial      not null
        constraint pk_employee
            primary key,
    lastname     varchar(20) not null,
    firstname    varchar(20) not null,
    departmentid integer
        constraint employee_departments
            references departments (departmentid),
    titleid      integer
        constraint employee_titles
            references titles
);

alter table employee
    owner to postgres;

INSERT INTO public.employee (employeeid, lastname, firstname, departmentid, titleid) VALUES (3, 'Petrov ', 'Petr', 2, 4);
INSERT INTO public.employee (employeeid, lastname, firstname, departmentid, titleid) VALUES (5, 'Shishkin', 'Shura', 2, 4);
INSERT INTO public.employee (employeeid, lastname, firstname, departmentid, titleid) VALUES (2, 'Ivanov', 'Ivan', 2, 4);
INSERT INTO public.employee (employeeid, lastname, firstname, departmentid, titleid) VALUES (4, 'Sidorov', 'Sidor', 3, 3);
INSERT INTO public.employee (employeeid, lastname, firstname, departmentid, titleid) VALUES (1, 'AA', 'A', 1, 1);

------------------------------------------------------------------------------------------------------------------------------

create table employee_salary
(
    employeeid integer not null
        constraint employee_salary_employee
            references employee,
    salaryid   integer not null
        constraint employee_salary_salaries
            references salaries,
    sdate      date    not null,
    salary     integer,
    percent    integer,
    constraint employee_salary_pkey
        primary key (employeeid, sdate)
);

alter table employee_salary
    owner to postgres;

INSERT INTO public.employee_salary (employeeid, salaryid, sdate, salary, percent) VALUES (5, 0, '2021-12-01', 8000, 0);
INSERT INTO public.employee_salary (employeeid, salaryid, sdate, salary, percent) VALUES (2, 2, '2021-12-02', 20202, 55);
INSERT INTO public.employee_salary (employeeid, salaryid, sdate, salary, percent) VALUES (4, 0, '2021-12-01', 15000, 0);
INSERT INTO public.employee_salary (employeeid, salaryid, sdate, salary, percent) VALUES (1, 0, '2021-12-11', 20210, 0);

-------------------------------------------------------------------------------------------------------------------------------

create table employee_schedule
(
    employeeid  integer not null
        constraint employee_schedule_pkey
            primary key
        constraint employee_schedule_employee
            references employee,
    scheduleid  integer not null
        constraint employee_schedule_schedule
            references workschedules,
    numberhours integer default 0
);

alter table employee_schedule
    owner to postgres;

INSERT INTO public.employee_schedule (employeeid, scheduleid, numberhours) VALUES (5, 1, 2);
INSERT INTO public.employee_schedule (employeeid, scheduleid, numberhours) VALUES (2, -1, -2);
INSERT INTO public.employee_schedule (employeeid, scheduleid, numberhours) VALUES (4, -1, -1);
INSERT INTO public.employee_schedule (employeeid, scheduleid, numberhours) VALUES (1, 1, 2);
