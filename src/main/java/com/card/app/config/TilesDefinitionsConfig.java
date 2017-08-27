package com.card.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.request.Request;

public final class TilesDefinitionsConfig implements DefinitionsFactory {

    private static final Map<String, Definition> tilesDefinitions = new HashMap<String,Definition>();
    private static final Attribute BASE_TEMPLATE = new Attribute("/WEB-INF/views/tiles/layouts/defaultLayout.jsp");

    public Definition getDefinition(String name, Request tilesContext) {
        return tilesDefinitions.get(name);
    }

    /**
     * @param name <code>Name of the view</code>
     * @param title <code>Page title</code>
     * @param body <code>Body JSP file path</code>
     *
     * <code>Adds default layout definitions</code>
     */
    private static void addDefaultLayoutDef(String name, String title, String body) {
        Map<String, Attribute> attributes = new HashMap<String,Attribute>();

        attributes.put("title", new Attribute(title));
        attributes.put("header", new Attribute("/WEB-INF/views/tiles/template/defaultHeader.jsp"));
        attributes.put("menu", new Attribute("/WEB-INF/views/tiles/template/defaultMenu.jsp"));
        attributes.put("body", new Attribute(body));
        attributes.put("footer", new Attribute("/WEB-INF/views/tiles/template/defaultFooter.jsp"));

        tilesDefinitions.put(name, new Definition(name, BASE_TEMPLATE, attributes));
    }

    /**
     * <code>Add Apache tiles definitions</code>
     */
    public static void addDefinitions(){
        /*HomeController*/
        addDefaultLayoutDef("home_index", "home_index", "/WEB-INF/views/home/home.jsp");
        /*UserController*/
        addDefaultLayoutDef("user-index", "user-index", "/WEB-INF/views/user/index.jsp");
        addDefaultLayoutDef("user-add", "user-add", "/WEB-INF/views/user/add.jsp");
        addDefaultLayoutDef("permission-index", "permission-index", "/WEB-INF/views/permission/index.jsp");
        addDefaultLayoutDef("permission-add", "permission-add", "/WEB-INF/views/permission/add.jsp");
        addDefaultLayoutDef("role-index", "role-index", "/WEB-INF/views/role/index.jsp");
        addDefaultLayoutDef("role-add", "role-add", "/WEB-INF/views/role/add.jsp");
         //Tutorial 
        addDefaultLayoutDef("tutorial-index", "tutorial-index", "/WEB-INF/views/tutorial/index.jsp");
        addDefaultLayoutDef("tutorial-add", "tutorial-add", "/WEB-INF/views/tutorial/add.jsp");
        addDefaultLayoutDef("tutorial-update", "tutorial-update", "/WEB-INF/views/tutorial/update.jsp");
        //Topic
        addDefaultLayoutDef("topic-index", "topic-index", "/WEB-INF/views/topic/index.jsp");
        addDefaultLayoutDef("topic-add", "topic-add", "/WEB-INF/views/topic/add.jsp");
        addDefaultLayoutDef("topic-update", "topic-update", "/WEB-INF/views/topic/update.jsp");
      //SubContent
        addDefaultLayoutDef("subcontent-index", "subcontent-index", "/WEB-INF/views/subcontent/index.jsp");
        addDefaultLayoutDef("subcontent-add", "subcontent-add", "/WEB-INF/views/subcontent/add.jsp");
        addDefaultLayoutDef("subcontent-update", "subcontent-update", "/WEB-INF/views/subcontent/update.jsp");
        //TopicContent
        addDefaultLayoutDef("topicContent-index", "topicContent-index", "/WEB-INF/views/topiccontent/index.jsp");
        addDefaultLayoutDef("topicContent-add", "topicContent-add", "/WEB-INF/views/topiccontent/add.jsp");
        addDefaultLayoutDef("topicContent-topic", "topicContent-topic", "/WEB-INF/views/topiccontent/topic.jsp");
        addDefaultLayoutDef("topicContent-update", "topicContent-update", "/WEB-INF/views/topiccontent/update.jsp");
        
        addDefaultLayoutDef("hello_index", "hello_index", "/WEB-INF/views/hello.jsp");
        

    }
}
