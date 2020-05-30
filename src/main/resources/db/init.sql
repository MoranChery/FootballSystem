CREATE TABLE football_system_db.subscriber
(
    `email_address`      VARCHAR(64) NOT NULL,
    `password`           VARCHAR(64) NOT NULL,
    `id`                 int         NOT NULL,
    `first_name`         VARCHAR(64) NOT NULL,
    `last_name`          VARCHAR(64) NOT NULL,
    `status`             VARCHAR(64) NOT NULL,
    `want_alert_in_mail` BIT(1)      NOT NULL,
    PRIMARY KEY (`email_address`)
);

CREATE TABLE football_system_db.coach
(
    `email_address`       VARCHAR(64) NOT NULL,
    `team`                VARCHAR(64),
    `coach_role`          VARCHAR(64) NOT NULL,
    `qualification_coach` VARCHAR(64) NOT NULL,
-- #     `coach_page`          VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_coach_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.court
(
    `court_name` VARCHAR(64) NOT NULL,
    `court_city` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`court_name`)
);

CREATE TABLE football_system_db.fan
(
    `email_address` VARCHAR(64) NOT NULL,
    `gamesAlert`    VARCHAR(64) NOT NULL,
    `alertWay`      BOOLEAN     NOT NULL,
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_fan_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.fan_page
(
    `email_address` VARCHAR(64) NOT NULL,
    `page_id`       VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`, `page_id`)
);

CREATE TABLE football_system_db.fan_search_history
(
    `email_address` VARCHAR(64) NOT NULL,
    `search`        VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`, `search`)
);

CREATE TABLE football_system_db.financial_activity
(
    `financial_activity_id`     VARCHAR(64) NOT NULL,
    `financial_activity_amount` DOUBLE      NOT NULL,
    `description`               VARCHAR(64) NOT NULL,
    `financial_activity_type`   VARCHAR(64) NOT NULL,
    `team`                      VARCHAR(64) NOT NULL,
    PRIMARY KEY (`financial_activity_id`)
);

CREATE TABLE football_system_db.game
(
    `game_id`          VARCHAR(64) NOT NULL,
    `end_game_time`    TIMESTAMP,
    `game_date`        TIMESTAMP   default current_timestamp,
    `season_league`    VARCHAR(64) NOT NULL,
    `host_team`        VARCHAR(64) NOT NULL,
    `guest_team`       VARCHAR(64) NOT NULL,
    `court`            VARCHAR(64) NOT NULL,
    `host_team_score`  INT,
    `guest_team_score` INT,
-- #     `event_log` VARCHAR(64) NOT NULL,
    `major_judge`      VARCHAR(64),
    PRIMARY KEY (`game_id`)
);

CREATE TABLE football_system_db.game_judges_list
(
    `game_id`              VARCHAR(64) NOT NULL,
    `judges_email_address` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`game_id`, `judges_email_address`)
);

CREATE TABLE football_system_db.game_event
(
    `game_id`         VARCHAR(64) NOT NULL,
    `event_id`        VARCHAR(64) NOT NULL,
    `game_date`       TIMESTAMP   NOT NULL,
    `event_time`      TIME        NOT NULL,
    `event_minute`    INT         NOT NULL,
    `game_event_type` VARCHAR(64) NOT NULL,
    `description`     VARCHAR(64) NOT NULL,
    PRIMARY KEY (`event_id`)
);

CREATE TABLE football_system_db.judge
(
    `email_address`       VARCHAR(64) NOT NULL,
    `qualification_judge` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_judge_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.judge_season_league
(
    `judge_season_league_name` VARCHAR(64) NOT NULL,
    `season_league_name`      VARCHAR(64) NOT NULL,
    `judge_email_address`     VARCHAR(64) NOT NULL,
    PRIMARY KEY (judge_season_league_name)
);

CREATE TABLE football_system_db.league
(
    `league_name` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`league_name`)
);

CREATE TABLE football_system_db.page
(
    `page_id` VARCHAR(64) NOT NULL,
    `page_type` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`page_id`)
);

CREATE TABLE football_system_db.permission
(
    `email_address`   VARCHAR(64) NOT NULL,
    `permission_type` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`, `permission_type`)
);

CREATE TABLE football_system_db.player
(
    `email_address` VARCHAR(64) NOT NULL,
    `team`          VARCHAR(64) DEFAULT NULL,
    `birth_date`    DATE        NOT NULL,
    `player_role`   VARCHAR(64) NOT NULL,
-- #     `player_page`   VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_player_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.representative_association
(
    `email_address` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_representative_association_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.role
(
    `email_address` VARCHAR(64) NOT NULL,
    `team_name`     VARCHAR(64) DEFAULT NULL,
    `role_type`     VARCHAR(64) NOT NULL,
    `assigned_date` mediumtext,
    PRIMARY KEY (`email_address`, `role_type`)
);

CREATE TABLE football_system_db.season
(
    `season_name` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`season_name`)
);

CREATE TABLE football_system_db.season_league
(
    `season_league_name` VARCHAR(64) NOT NULL,
    `season_name` VARCHAR(64) NOT NULL,
    `league_name` VARCHAR(64) NOT NULL,
    `calculate_league_points` VARCHAR(64) NOT NULL,
    `inlay_games` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`season_league_name`)
);

CREATE TABLE football_system_db.system_administrator
(
    `email_address` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_system_administrator_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.team
(
    `team_name`   VARCHAR(64) NOT NULL,
    `budget`      DOUBLE      NOT NULL,
    `team_status` VARCHAR(64) NOT NULL,
    `court` VARCHAR(64),
    `team_close` VARCHAR(64),
    PRIMARY KEY (`team_name`)
);

CREATE TABLE football_system_db.team_manager
(
    `email_address` VARCHAR(64) NOT NULL,
    `team` VARCHAR(64),
    `owned_by_email` VARCHAR(64),
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_team_manager_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.team_owner
(
    `email_address` VARCHAR(64) NOT NULL,
    `team` VARCHAR(64),
    `owned_by_email_address` VARCHAR(64),
    PRIMARY KEY (`email_address`),
    CONSTRAINT FK_team_owner_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.alert
(
    `email_address` VARCHAR(64) NOT NULL,
    `alert_id`      VARCHAR(64) NOT NULL,
    `msg_header`    VARCHAR(200) NOT NULL,
    `msg_body`      VARCHAR(400) NOT NULL,
    PRIMARY KEY (`email_address`, `alert_id`)
);

CREATE TABLE football_system_db.search
(
    `search_id` INT         NOT NULL,
    `key_words` VARCHAR(64) NOT NULL,
    `name`      VARCHAR(64) NOT NULL,
    `category`  VARCHAR(64) NOT NULL,
    PRIMARY KEY (`search_id`)
);

-- # CREATE TABLE football_system_db.personal_page
-- # (
-- #     `email_address` VARCHAR(64) NOT NULL,
-- #     `page_id`       VARCHAR(64) NOT NULL,
-- #     PRIMARY KEY (`email_address`, `page_id`),
-- #     CONSTRAINT FK_personal_page_email_subscriber
-- #         FOREIGN KEY (`email_address`)
-- #             REFERENCES football_system_db.subscriber (`email_address`)
-- #             ON DELETE CASCADE
-- #             ON UPDATE CASCADE,
-- #     CONSTRAINT FK_personal_page_page_id
-- #         FOREIGN KEY (`page_id`)
-- #             REFERENCES football_system_db.page (`page_id`)
-- #             ON DELETE CASCADE
-- #             ON UPDATE CASCADE
-- # );

-- # CREATE TABLE football_system_db.team_page
-- # (
-- #     `team`    VARCHAR(64) NOT NULL,
-- #     `page_id` VARCHAR(64) NOT NULL,
-- #     PRIMARY KEY (`team`, `page_id`),
-- #     CONSTRAINT FK_team_page_team
-- #         FOREIGN KEY (`team`)
-- #             REFERENCES football_system_db.team (`team_name`)
-- #             ON DELETE CASCADE
-- #             ON UPDATE CASCADE,
-- #     CONSTRAINT FK_team_page_page_id
-- #         FOREIGN KEY (`page_id`)
-- #             REFERENCES football_system_db.page (`page_id`)
-- #             ON DELETE CASCADE
-- #             ON UPDATE CASCADE
-- # );

CREATE TABLE football_system_db.complaint
(
    `email_address` VARCHAR(64) NOT NULL,
    `description`   VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`, `description`),
    CONSTRAINT FK_complaint_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE football_system_db.response
(
    `email_address` VARCHAR(64) NOT NULL,
    `description`   VARCHAR(64) NOT NULL,
    PRIMARY KEY (`email_address`, `description`),
    CONSTRAINT FK_response_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);



--
-- ALTER TABLE football_system_db.page
--     ADD CONSTRAINT FK_coach_page
--         FOREIGN KEY (`page_id`)
--             REFERENCES football_system_db.coach (`email_address`)
--             ON DELETE CASCADE
--             ON UPDATE CASCADE
-- ;


-- ALTER TABLE football_system_db.page
--     ADD CONSTRAINT FK_coach_page
--         FOREIGN KEY (`page_id`)
--             REFERENCES football_system_db.player (`email_address`)
--             ON DELETE CASCADE
--             ON UPDATE CASCADE
-- ;

ALTER TABLE football_system_db.fan_page
    ADD CONSTRAINT FK_fan_page_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_fan_page_page_id
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.page (`page_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.fan_search_history
    ADD CONSTRAINT FK_fan_search_history_email_subscriber
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.financial_activity
    ADD CONSTRAINT FK_financialActivity_teamName
        FOREIGN KEY (`team`)
            REFERENCES football_system_db.team (`team_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.game
    ADD CONSTRAINT FK_game_season_league
        FOREIGN KEY (`season_league`)
            REFERENCES football_system_db.season_league (`season_league_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_game_host_team
        FOREIGN KEY (`host_team`)
            REFERENCES football_system_db.team (`team_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_game_guest_team
        FOREIGN KEY (`guest_team`)
            REFERENCES football_system_db.team (`team_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_game_court
        FOREIGN KEY (`court`)
            REFERENCES football_system_db.court (`court_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_game_majorJudge_email_address
        FOREIGN KEY (`major_judge`)
            REFERENCES football_system_db.judge (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.game_judges_list
    ADD CONSTRAINT FK_game_judges_list_game_id
        FOREIGN KEY (`game_id`)
            REFERENCES football_system_db.game (`game_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_game_judges_list_judge_email_address
        FOREIGN KEY (`judges_email_address`)
            REFERENCES football_system_db.judge (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.game_event
    ADD CONSTRAINT FK_game_event_game_id
        FOREIGN KEY (`game_id`)
            REFERENCES football_system_db.game (`game_id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.judge_season_league
    ADD CONSTRAINT FK_judge_season_league_season_league_name
        FOREIGN KEY (`season_league_name`)
            REFERENCES football_system_db.season_league (`season_league_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_judge_season_league_judge_email_address
        FOREIGN KEY (`judge_email_address`)
            REFERENCES football_system_db.judge (email_address)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.permission
    ADD CONSTRAINT FK_permission_email_address
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.player
    ADD CONSTRAINT FK_player_team
        FOREIGN KEY (`team`)
            REFERENCES football_system_db.team (`team_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
--     ,ADD CONSTRAINT FK_player_page
--         FOREIGN KEY (`email_address`)
--             REFERENCES football_system_db.page (`page_id`)
--             ON DELETE CASCADE
--             ON UPDATE CASCADE
;

ALTER TABLE football_system_db.role
    ADD CONSTRAINT FK_role_email_address
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_role_team
        FOREIGN KEY (`team_name`)
            REFERENCES football_system_db.team (`team_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;



ALTER TABLE football_system_db.season_league
    ADD CONSTRAINT FK_season_league_season_name
        FOREIGN KEY (`season_name`)
            REFERENCES football_system_db.season (`season_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_season_league_league_name
        FOREIGN KEY (`league_name`)
            REFERENCES football_system_db.league (`league_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.team_manager
    ADD CONSTRAINT FK_team_manager_team
        FOREIGN KEY (`team`)
            REFERENCES football_system_db.team (`team_name`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
--             ,
--     ADD CONSTRAINT FK_team_manager_owned_by_email
--         FOREIGN KEY (`owned_by_email`)
--             REFERENCES football_system_db.team_owner (`email_address`)
--             ON DELETE CASCADE
--             ON UPDATE CASCADE
;

ALTER TABLE football_system_db.team_owner
    --     ADD CONSTRAINT FK_team_owner_team
--         FOREIGN KEY (`team`)
--             REFERENCES football_system_db.team (`team_name`)
--             ON DELETE CASCADE
--             ON UPDATE CASCADE,
    ADD CONSTRAINT FK_team_owner_owned_by_email
        FOREIGN KEY (`owned_by_email_address`)
            REFERENCES football_system_db.team_owner (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;

ALTER TABLE football_system_db.alert
    ADD CONSTRAINT FK_alert_email_address
        FOREIGN KEY (`email_address`)
            REFERENCES football_system_db.subscriber (`email_address`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
;