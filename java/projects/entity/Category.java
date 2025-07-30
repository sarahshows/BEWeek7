/**
 * 
 */
package projects.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Promineo
 *
 */


public class Category {
  private Integer categoryId;
  private String categoryName;
  List<Category> catergories = new LinkedList<>();

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public String toString() {
    return "ID=" + categoryId + ", categoryName=" + categoryName;
  }
}
