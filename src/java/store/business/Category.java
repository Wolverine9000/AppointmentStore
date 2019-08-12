/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.util.Collection;

/**
 *
 * @author williamdobbs
 */
public class Category
{

    private String name;
    private int category_id;
    private Collection<Product> productCollection;
    private Object id;

    public Category()
      {
        name = "";
        category_id = 0;
      }
    
        public Category(int category_id) {
        this.category_id = category_id;
    }

    public Category(int category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public int getCategory_id()
      {
        return category_id;
      }

    public void setCategory_id(int category_id)
      {
        this.category_id = category_id;
      }

    public String getName()
      {
        return name;
      }

    public void setName(String name)
      {
        this.name = name;
      }
    
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Category[id=" + id + "]";
    }
}   
