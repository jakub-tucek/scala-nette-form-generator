package service

import domain.sql.{ColumnVarchar, Table, TableColumn}
import org.scalatest.{FlatSpec, Matchers}

/**
  *
  * @author Jakub Tucek
  */
class FormTemplateServiceSpec extends FlatSpec with Matchers {

  "FormTemplateSericeSpec" should "Convert table to correct html" in {
    val service = new FormTemplateService


    val tables = List(Table("my_table", List(TableColumn("col", ColumnVarchar(), List()))))

    val result = service.createLatteTemplates(tables)

    result.templates should have size 1

    result.templates.map(_.templateName) should contain("my_table")
  }
}
