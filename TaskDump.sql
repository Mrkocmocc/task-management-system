PGDMP                      |            task-manage-db    16.3    16.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16872    task-manage-db    DATABASE     �   CREATE DATABASE "task-manage-db" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
     DROP DATABASE "task-manage-db";
                postgres    false            S           1247    16944    task_priority    TYPE     R   CREATE TYPE public.task_priority AS ENUM (
    'LOW',
    'MEDIUM',
    'HIGH'
);
     DROP TYPE public.task_priority;
       public          postgres    false            P           1247    16936    task_status    TYPE     V   CREATE TYPE public.task_status AS ENUM (
    'TODO',
    'IN_PROGRESS',
    'DONE'
);
    DROP TYPE public.task_status;
       public          postgres    false            �            1259    17017    comment    TABLE     �   CREATE TABLE public.comment (
    id bigint NOT NULL,
    content character varying(255) NOT NULL,
    author_id bigint NOT NULL,
    task_id bigint NOT NULL
);
    DROP TABLE public.comment;
       public         heap    postgres    false            �            1259    17016    comment_id_seq    SEQUENCE     �   ALTER TABLE public.comment ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            �            1259    16957    tasks    TABLE     h  CREATE TABLE public.tasks (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    status character varying(20) DEFAULT 'TODO'::public.task_status NOT NULL,
    priority character varying(20) DEFAULT 'LOW'::public.task_priority NOT NULL,
    author_id bigint NOT NULL,
    executor_id bigint
);
    DROP TABLE public.tasks;
       public         heap    postgres    false    848    851            �            1259    16956    tasks_id_seq    SEQUENCE     �   ALTER TABLE public.tasks ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    16874    users    TABLE     �   CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16873    users_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216                       0    17017    comment 
   TABLE DATA           B   COPY public.comment (id, content, author_id, task_id) FROM stdin;
    public          postgres    false    220   �       �          0    16957    tasks 
   TABLE DATA           a   COPY public.tasks (id, title, description, status, priority, author_id, executor_id) FROM stdin;
    public          postgres    false    218   �       �          0    16874    users 
   TABLE DATA           4   COPY public.users (id, email, password) FROM stdin;
    public          postgres    false    216                     0    0    comment_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.comment_id_seq', 1, true);
          public          postgres    false    219                       0    0    tasks_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.tasks_id_seq', 13, true);
          public          postgres    false    217            	           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 17, true);
          public          postgres    false    215            g           2606    17023    comment comment_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.comment DROP CONSTRAINT comment_pkey;
       public            postgres    false    220            e           2606    16965    tasks tasks_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.tasks DROP CONSTRAINT tasks_pkey;
       public            postgres    false    218            c           2606    16880    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    216            h           2606    16966    tasks author_fk    FK CONSTRAINT     z   ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT author_fk FOREIGN KEY (author_id) REFERENCES public.users(id) NOT VALID;
 9   ALTER TABLE ONLY public.tasks DROP CONSTRAINT author_fk;
       public          postgres    false    216    218    4707            j           2606    17025    comment author_fk    FK CONSTRAINT     |   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT author_fk FOREIGN KEY (author_id) REFERENCES public.users(id) NOT VALID;
 ;   ALTER TABLE ONLY public.comment DROP CONSTRAINT author_fk;
       public          postgres    false    220    4707    216            i           2606    16971    tasks executor_fk    FK CONSTRAINT     ~   ALTER TABLE ONLY public.tasks
    ADD CONSTRAINT executor_fk FOREIGN KEY (executor_id) REFERENCES public.users(id) NOT VALID;
 ;   ALTER TABLE ONLY public.tasks DROP CONSTRAINT executor_fk;
       public          postgres    false    216    4707    218            k           2606    17030    comment task_fk    FK CONSTRAINT     x   ALTER TABLE ONLY public.comment
    ADD CONSTRAINT task_fk FOREIGN KEY (task_id) REFERENCES public.tasks(id) NOT VALID;
 9   ALTER TABLE ONLY public.comment DROP CONSTRAINT task_fk;
       public          postgres    false    4709    218    220                   x�3�,.)��K�44�4����� 2�1      �   O   x�3�,I-.��K��
)���E�%��y�!�.��>�ᜆ�1~\�05FD(64�,/�����9�K�@΀Rh�1z\\\ �45      �   �   x�]���   �s<�g��1�\���"5ץ�m��b���:�=��Vjn�F�x�ZY��B�U�S�Ӭa��#�
�N>�fs>:(Z�z���6X�u8�����m���T]��#e��jRx���gTw$�Ҕi#��(�+�ֿ�o���%lū>&|:���{y����r4*�NE_�Ӎ��� �1�K     