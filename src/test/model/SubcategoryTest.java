package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubcategoryTest {

    private Subcategory subcategory;
    private Category category;
    private List<String> tagList;
    private List<String> emptyList;
    
    @BeforeEach
    void setup() {
        tagList = new ArrayList<String>();
        tagList.add("tag1");
        tagList.add("tag2");

        category = new Category();

        emptyList = new ArrayList<String>();

        subcategory = new Subcategory(category, 1, tagList, "", "", emptyList, emptyList);
    }

    @Test
    void testSubcategoryConstructor() {
        assertEquals(category, subcategory.getParentCategory());
        assertEquals(1, subcategory.getPriorityLevel());
        assertEquals(tagList, subcategory.getTags());
        assertEquals("", subcategory.getName());
        assertEquals("", subcategory.getLocation());
        assertEquals(emptyList, subcategory.getLinks());
        assertEquals(emptyList, subcategory.getNotes());
    }

    @Test
    void testAddTag() {
        subcategory.addTag("tag3");
        tagList.add("tag3");

        assertEquals(tagList, subcategory.getTags());
    }

    @Test
    void testAddTagDuplicate() {
        subcategory.addTag("tag2");

        assertEquals(tagList, subcategory.getTags());
    }

    @Test
    void testRemoveTag() {
        subcategory.removeTag("tag2");

        assertEquals(tagList, subcategory.getTags());
    }

    @Test
    void testRemoveTagFail() {
        subcategory.removeTag("tag3");
        
        assertEquals(tagList, subcategory.getTags());
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
        Category parentSubcategory = new Subcategory(null, 1, emptyList, "", "", emptyList, emptyList);
        subcategory.setParentCategory(parentSubcategory);
        assertTrue(subcategory.isParent(parentSubcategory));
    }

     @Test
    void testToJsonWithNullParentCategory() {
        JSONObject json = subcategory.toJson();

        assertTrue(json.getInt("id") == (int)json.get("id"));
        assertEquals(subcategory.getName(), json.get("name"));
        assertEquals(subcategory.getLocation(), json.get("location"));
        assertEquals(new JSONArray(subcategory.getLinks()).toString(), json.get("links").toString());
        assertEquals(new JSONArray(subcategory.getNotes()).toString(), json.get("notes").toString());
        assertNotNull(json.get("parentCategory"));
    }

}
