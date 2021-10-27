create table bills (id  bigserial not null, date date, description varchar(255), price numeric(19, 2), provider varchar(255), timestamp Timestamp default current_timestamp, type int4, valid_until date, car_id int8, image_id varchar(255), user_id int8, primary key (id));
create table cars (id  bigserial not null, casco_date date, itp_date date, make varchar(255), model varchar(255), rca_date date, registration varchar(255), revision_date date, type varchar(255), vignette_date date, user_id int8, primary key (id));
create table images (id varchar(255) not null, content_type varchar(255), data bytea, name varchar(255), size int8, primary key (id));
create table users (id  bigserial not null, email varchar(255), email_notification boolean, name varchar(255), password varchar(255), phone_number varchar(255), role int4, sms_notification boolean, username varchar(255), primary key (id));
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table bills add constraint FKca6qp8h7nywor437hfrws1t6b foreign key (car_id) references cars on delete cascade;
alter table bills add constraint FK74kka1aefv3im1l7wgyxiiv78 foreign key (image_id) references images;
alter table bills add constraint FKk8vs7ac9xknv5xp18pdiehpp1 foreign key (user_id) references users;
alter table cars add constraint FKqw4c8e6nqrvy3ti1xj8w8wyc9 foreign key (user_id) references users;