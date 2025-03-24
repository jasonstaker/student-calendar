package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    
    private Category category1;
    private Category category2;
    private Category category3;

    private Subcategory subcategory1;
    private Subcategory subcategory2;

    private List<Subcategory> scList;
    private List<String> emptyList;

    @BeforeEach
    void setup() {
        emptyList = new ArrayList<String>();
        subcategory1 = new Subcategory(category1, 1, emptyList, "", "", emptyList, emptyList);
        subcategory1 = new Subcategory(category1, 2, emptyList, "", "", emptyList, emptyList);
        
        scList = new ArrayList<Subcategory>();
        scList.add(subcategory1);
        scList.add(subcategory2);

        category1 = new Category();
        category2 = new Category("test");
        category3 = new Category("", scList, "", emptyList, emptyList);
        category3.setNotes(emptyList);
    }

    @Test
    void testCategoryConstructorDefault() {
        assertEquals("", category1.getName());
        assertEquals(new ArrayList<Subcategory>(), category1.getSubcategories());
        assertEquals("", category1.getLocation());
        assertEquals(emptyList, category1.getLinks());
        assertEquals(emptyList, category1.getNotes());
        assertTrue(category1.getId() == (int)category1.getId());
    }

    @Test
    void testCategoryConstructorName() {
        assertEquals("test", category2.getName());
        assertEquals(new ArrayList<Subcategory>(), category2.getSubcategories());
        assertEquals("", category2.getLocation());
        assertEquals(emptyList, category2.getLinks());
        assertEquals(emptyList, category2.getNotes());
    }

    @Test
    void testCategoryConstructorList() {
        assertEquals("", category3.getName());
        assertEquals(scList, category3.getSubcategories());
        assertEquals("", category3.getLocation());
        assertEquals(emptyList, category3.getLinks());
        assertEquals(emptyList, category3.getNotes());
    }

    @Test
    void testAddSubcategory() {
        category1.addSubcategory(subcategory1);

        assertEquals(subcategory1, category1.getSubcategories().get(0));
        assertEquals(1, category1.getSubcategories().size());
    }

    @Test
    void testAddSubcategories() {
        category1.addSubcategory(subcategory1);
        category1.addSubcategory(subcategory2);

        assertEquals(subcategory1, category1.getSubcategories().get(0));
        assertEquals(subcategory2, category1.getSubcategories().get(1));
        assertEquals(2, category1.getSubcategories().size());
    }

    @Test
    void testRemoveSubcategory() {
        category3.removeSubcategory(subcategory1);

        assertEquals(subcategory2, category3.getSubcategories().get(0));
        assertEquals(1, category3.getSubcategories().size());
    }

    @Test
    void testRemoveSubcategories() {
        category3.removeSubcategory(subcategory2);
        category3.removeSubcategory(subcategory1);

        assertEquals(0, category3.getSubcategories().size());
    }

    @Test
    void testAddLink() {
        category1.addLink("url1");

        assertEquals("url1", category1.getLinks().get(0));
        assertEquals(1, category1.getLinks().size());
    }

    @Test
    void testAddLinks() {
        category1.addLink("url1");
        category1.addLink("url2");

        assertEquals("url1", category1.getLinks().get(0));
        assertEquals("url2", category1.getLinks().get(1));
        assertEquals(2, category1.getLinks().size());
    }

    @Test
    void testRemoveLinkString() {
        category1.addLink("url1");
        category1.removeLink("url1");

        assertEquals(0, category1.getLinks().size());
    }

    @Test
    void testRemoveLinkInt() {
        category1.addLink("url1");
        category1.removeLink(0);

        assertEquals(0, category1.getLinks().size());
    }

    @Test
    void testRemoveLinkStringDeep() {
        category1.addLink("url1");
        category1.addLink("url2");
        category1.removeLink("url2");

        assertEquals("url1", category1.getLinks().get(0));
        assertEquals(1, category1.getLinks().size());
    }

    @Test
    void testRemoveLinkIntDeep() {
        category1.addLink("url1");
        category1.addLink("url2");
        category1.removeLink(1);

        assertEquals("url1", category1.getLinks().get(0));
        assertEquals(1, category1.getLinks().size());
    }

    @Test
    void testAddNote() {
        category1.addNote("note1");

        assertEquals("note1", category1.getNotes().get(0));
        assertEquals(1, category1.getNotes().size());
    }

    @Test
    void testAddNotes() {
        category1.addNote("note1");
        category1.addNote("note2");

        assertEquals("note1", category1.getNotes().get(0));
        assertEquals("note2", category1.getNotes().get(1));
        assertEquals(2, category1.getNotes().size());
    }

    @Test
    void testRemoveNoteString() {
        category1.addNote("note1");
        category1.removeNote("note1");

        assertEquals(0, category1.getNotes().size());
    }

    @Test
    void testRemoveNoteInt() {
        category1.addNote("note1");
        category1.removeNote(0);

        assertEquals(0, category1.getNotes().size());
    }

    @Test
    void testRemoveNoteStringDeep() {
        category1.addNote("note1");
        category1.addNote("note2");
        category1.removeNote("note2");

        assertEquals("note1", category1.getNotes().get(0));
        assertEquals(1, category1.getNotes().size());
    }

    @Test
    void testRemoveNoteIntDeep() {
        category1.addNote("note1");
        category1.addNote("note2");
        category1.removeNote(1);

        assertEquals("note1", category1.getNotes().get(0));
        assertEquals(1, category1.getNotes().size());
    }

    

}
