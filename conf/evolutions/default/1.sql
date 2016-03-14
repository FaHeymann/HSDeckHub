# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table card (
  dtype                     varchar(10) not null,
  id                        integer auto_increment not null,
  name                      varchar(255) not null,
  description               varchar(255) not null,
  attack                    integer,
  life                      integer,
  cost                      integer not null,
  class_id                  integer not null,
  quality_id                integer not null,
  race_id                   integer not null,
  has_aura                  tinyint(1) default 0 not null,
  has_battle_cry            tinyint(1) default 0 not null,
  has_charge                tinyint(1) default 0 not null,
  has_combo                 tinyint(1) default 0 not null,
  has_death_rattle          tinyint(1) default 0 not null,
  has_divine_shield         tinyint(1) default 0 not null,
  has_enrage                tinyint(1) default 0 not null,
  has_freeze                tinyint(1) default 0 not null,
  has_inspire               tinyint(1) default 0 not null,
  has_ogre                  tinyint(1) default 0 not null,
  has_overload              tinyint(1) default 0 not null,
  has_poison                tinyint(1) default 0 not null,
  has_secret                tinyint(1) default 0 not null,
  has_spellpower            tinyint(1) default 0 not null,
  has_stealth               tinyint(1) default 0 not null,
  has_taunt                 tinyint(1) default 0 not null,
  has_windfury              tinyint(1) default 0 not null,
  constraint pk_card primary key (id))
;

create table deck (
  id                        integer auto_increment not null,
  name                      varchar(255) not null,
  description               varchar(255),
  user_id                   integer not null,
  constraint pk_deck primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;


create table card_deck_first (
  deck_id                        integer not null,
  card_id                        integer not null,
  constraint pk_card_deck_first primary key (deck_id, card_id))
;

create table card_deck_second (
  deck_id                        integer not null,
  card_id                        integer not null,
  constraint pk_card_deck_second primary key (deck_id, card_id))
;
alter table deck add constraint fk_deck_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_deck_user_1 on deck (user_id);



alter table card_deck_first add constraint fk_card_deck_first_deck_01 foreign key (deck_id) references deck (id) on delete restrict on update restrict;

alter table card_deck_first add constraint fk_card_deck_first_card_02 foreign key (card_id) references card (id) on delete restrict on update restrict;

alter table card_deck_second add constraint fk_card_deck_second_deck_01 foreign key (deck_id) references deck (id) on delete restrict on update restrict;

alter table card_deck_second add constraint fk_card_deck_second_card_02 foreign key (card_id) references card (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table card;

drop table deck;

drop table card_deck_first;

drop table card_deck_second;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

