# jcsvorm

[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0) 

Use annotation and reflection to handle the ORM (Object-Relational Mapping) between Java classes and CSV (Comma-Separated Values) files.

## Overview
A comma-separated values (CSV) file is a delimited text file that uses a comma to separate values. The structure of CSV file content is table which each line is a data record and it has one or more columns, which is very similar to a table in relational database. In java, there is the Hibernate framework for mapping an object-oriented domain model to a relational database. So why don't we have a framework which can map an object-oriented domain model to a CSV file.

In jcsvorm, it uses @CsvColumn with parameter "name" to handle the mapping between a field in java and a column in CSV file, like

**In Java**
```java
@CsvColumn(name = "first name")
private String firstName;
```

**In CSV**

| first name |
| - |
| Jack |
| Mary |
| ...  |

## Getting Started
Please see our [Wiki](https://github.com/wuyichen24/jcsvorm/wiki/Getting-Started) page.

## Documentation
Please see our [Wiki](https://github.com/wuyichen24/jcsvorm/wiki) page.

## Download
- [Download ZIP](https://github.com/wuyichen24/jcsvorm/archive/master.zip)
- [Download JAR](https://github.com/wuyichen24/jcsvorm/releases/download/v1.1/jcsvorm-1.1.jar)

## Contributing

## License
[Apache-2.0](https://opensource.org/licenses/Apache-2.0)

## Authors
- **[Wuyi Chen](https://www.linkedin.com/in/wuyichen24/)**
