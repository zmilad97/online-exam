INSERT INTO User
(
`id`,
 `username`,
 `name`,
 `password`,
 `birthdate` ,
 `email`,
 `gender` ,
 `permissions` ,
 `active`
 )
 VALUES (
       1,
        'admin',
        'milad zaeri',
        'milad',
        null ,
        'zmilad97@gmail.com',
        0,
        'USER,ADMIN,MASTER' ,
        1

);
 INSERT INTO `exam`(`id`, `title`,`active`, `maker_id`,`minus_score`, `show_answer`, `show_score`, `time`) VALUES (1,'test',1,1,0,0,0,5);
 INSERT INTO `question`(`id`, `correct`, `description`, `exam_id`) VALUES (1,2,'this is 1st question',1);
 INSERT INTO `scores`(`id`, `answer`, `exam_id`, `question_id`, `user_id`) VALUES (5,2,1,1,1);

INSERT INTO User
(
`id`,
 `username`,
 `name`,
 `password`,
 `birthdate` ,
 `email`,
 `gender` ,
 `permissions` ,
 `active`
 )
 VALUES (
       3,
        'test',
        'test zaeri',
        'test',
        null ,
        'test@gmail.com',
        0,
        'USER,ADMIN,MASTER' ,
        1
);