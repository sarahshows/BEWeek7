DROP TABLE IF TABLE EXISTS PROJECT_CATEGORY;
DROP TABLE IF TABLE EXISTS CATEGORY;
DROP TABLE IF TABLE EXISTS STEP;
DROP TABLE IF TABLE EXISTS MATERIAL;
DROP TABLE IF TABLE EXISTS PRJECT;

// PROJECT table
CREATE TABLE project (
	project_id INT NOT NULL AUTO INCREMENT,  	//Primary Key
	project_name VARCHAR(128) NOT NULL,		
	estimated_hours DECIMAL(7,2) NULL,
	actual_hours DECIMAL(7,2) NULL,
	difficulty INT NULL,
	notes TEXT NULL
)

// MATERIAL table
CREATE TABLE material (
	material_id INT NOT NULL,				//Primary Key
	project_id INT NOT NULL,   				//Foreign key
	material_name VARCHAR(128) NOT NULL,
	num_required INT NULL,
	cost DECIMAL(7,2) NULL
)

// STEP table
CREATE TABLE step (
	step_id INT NOT NULL AUTO INCREMENT,  	//PRIMARY KEY
	project_id INT NOT NULL,   				//Foreign key
	step_text TEXT NOT NULL,
	step_order INT NOT NULL
)

//CATEGORY table
CREATE TABLE category (
	category_id INT NOT NULL AUTO_INCREMENT,	//FOREIGN KEY, UNIQUE KEY WITH CATEGORY_ID
	category_name VARCHAR(128) NOT NULL
)

//PROJECT CATEGORY table
CREATE TABLE project_category (
	project_id INT NOT NULL,	//foreign key, unique key with category_id
	category_id INT NOT NULL	//foreign key, unique key with project_id
)
