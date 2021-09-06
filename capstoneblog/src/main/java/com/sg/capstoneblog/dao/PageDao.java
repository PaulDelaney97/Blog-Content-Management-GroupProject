/**
 *@author
 *date:
 *purpose:
 */
package com.sg.capstoneblog.dao;

import com.sg.capstoneblog.dto.Page;
import java.util.List;

public interface PageDao {

    Page getPageById(int id);

    List<Page> getAllPages();

    Page addPage(Page page);

    void editPage(Page page);

    void deletePage(int id);
}
