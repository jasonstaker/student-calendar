package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubcategoryTest {

    Subcategory subcategory;
    Category category;
    List<String> tagList;
    
    @BeforeEach
    void setup() {
        tagList = new ArrayList<String>();
        tagList.add("tag1");
        tagList.add("tag2");

        subcategory = new Subcategory(category, 1, tagList);
    }

    @Test
    void testSubcategoryConstructor() {
        assertEquals(category, subcategory.getParentCategory());
        assertEquals(1, subcategory.getPriorityLevel());
        assertEquals(tagList, subcategory.getTags());
    }

    @Test
    void testAddTag() {
        subcategory.addTag("tag3");
        assertEquals(3, subcategory.getTags().size());
        assertEquals("tag3", subcategory.getTags().get(2));
    }

    @Test
    void testAddTagDuplicate() {
        subcategory.addTag("tag2");
        assertEquals(2, subcategory.getTags().size());
    }

    @Test
    void testRemoveTag() {
        subcategory.addTag("tag2");
        assertEquals(1, subcategory.getTags().size());
        assertEquals("tag1", subcategory.getTags().get(0));
    }

    @Test
    void testRemoveTagFail() {
        subcategory.addTag("tag3");
        assertEquals(2, subcategory.getTags().size());
    }

    @Test
    void testPriorityLevelToStringLow() {
        subcategory.setPriorityLevel(1);
        assertEquals("Low", subcategory.priorityLevelToString());
    }

    @Test
    void testPriorityLevelToStringMedium() {
        subcategory.setPriorityLevel(2);
        assertEquals("Medium", subcategory.priorityLevelToString());
    }

    @Test
    void testPriorityLevelToStringHigh() {
        subcategory.setPriorityLevel(3);
        assertEquals("High", subcategory.priorityLevelToString());
    }

    @Test
    void testIsParentPass() {
        assertTrue(subcategory.isParent(category));
    }

    @Test
    void testIsParentFail() {
        Category failCategory = new Category();
        assertFalse(subcategory.isParent(failCategory));
    }

    @Test
    void testIsParentAbstract() {
        Category parentSubcategory = new Subcategory(null, 1, null);
        subcategory.setParentCategory(parentSubcategory);
        assertFalse(subcategory.isParent(parentSubcategory));
    }

}
