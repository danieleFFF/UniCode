
INSERT INTO public.avatar (id, url_image) VALUES
(1, 'assets/images/avatar/avatar1.png'),
(2, 'assets/images/avatar/avatar2.png'),
(3, 'assets/images/avatar/avatar3.png'),
(4, 'assets/images/avatar/avatar4.png'),
(5, 'assets/images/avatar/avatar5.png'),
(6, 'assets/images/avatar/avatar6.png'),
(7, 'assets/images/avatar/avatar7.png'),
(8, 'assets/images/avatar/avatar8.png'),
(9, 'assets/images/avatar/avatar9.png'),
(10, 'assets/images/avatar/avatar10.png'),
(11, 'assets/images/avatar/avatar11.png'),
(12, 'assets/images/avatar/avatar12.png'),
(13, 'assets/images/avatar/avatar13.png')
ON CONFLICT (id) 
DO UPDATE SET url_image = EXCLUDED.url_image;

SELECT setval('avatar_id_seq', 13, true);