/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.topology.app.internal;

import java.util.ArrayList;
import java.util.List;

import org.opennms.features.topology.api.GraphContainer;
import org.opennms.features.topology.api.support.VertexHopGraphProvider;
import org.opennms.features.topology.api.topo.AbstractSearchProvider;
import org.opennms.features.topology.api.topo.CollapsibleCriteria;
import org.opennms.features.topology.api.topo.SearchProvider;
import org.opennms.features.topology.api.topo.SearchQuery;
import org.opennms.features.topology.api.topo.SearchResult;
import org.opennms.features.topology.api.topo.VertexRef;
import org.opennms.features.topology.app.internal.support.CategoryHopCriteria;
import org.opennms.features.topology.app.internal.support.CategoryHopCriteriaFactory;
import org.opennms.netmgt.dao.api.CategoryDao;
import org.opennms.netmgt.dao.api.NodeDao;

public class CategorySearchProvider extends AbstractSearchProvider implements SearchProvider {

    private CategoryHopCriteriaFactory m_categoryHopFactory;
    private CategoryDao m_categoryDao;

    public CategorySearchProvider(CategoryDao categoryDao, NodeDao nodeDao){
        m_categoryDao = categoryDao;
        m_categoryHopFactory = new CategoryHopCriteriaFactory(categoryDao, nodeDao);
    }

    @Override
    public String getSearchProviderNamespace() {
        return "category";
    }

    @Override
    public boolean contributesTo(String namespace) {
        return "nodes".equals(namespace);
    }

    @Override
    public List<SearchResult> query(SearchQuery searchQuery) {
        List<String> categories = m_categoryDao.getAllCategoryNames();
        List<SearchResult> results = new ArrayList<SearchResult>();
        for (String category : categories) {
            if(searchQuery.matches(category)){
                SearchResult result = new SearchResult("category", category, category);
                result.setCollapsible(true);
                results.add(result);
            }
        }
        return results;
    }

    @Override
    public boolean supportsPrefix(String searchPrefix) {
        return supportsPrefix("category=", searchPrefix);
    }

    @Override
    public List<VertexRef> getVertexRefsBy(SearchResult searchResult) {
        return null;
    }

    @Override
    public void addVertexHopCriteria(SearchResult searchResult, GraphContainer container) {
        CategoryHopCriteria criteria = m_categoryHopFactory.getCriteria(searchResult.getId());
        criteria.setId(searchResult.getId());
        container.addCriteria(criteria);
    }

    @Override
    public void removeVertexHopCriteria(SearchResult searchResult, GraphContainer container) {
        CategoryHopCriteria c = m_categoryHopFactory.getCriteria(searchResult.getId());
        c.setId(searchResult.getId());
        container.removeCriteria(c);
    }

    public CategoryDao getCategoryDao() {
        return m_categoryDao;
    }

    public void setCategoryDao(CategoryDao m_categoryDao) {
        this.m_categoryDao = m_categoryDao;
    }

    @Override
    public void onToggleCollapse(SearchResult searchResult, GraphContainer graphContainer) {
        CollapsibleCriteria[] criteria = VertexHopGraphProvider.getCollapsibleCriteriaForContainer(graphContainer);
        for (CollapsibleCriteria criterium : criteria) {
            if (criterium.getId().equals(searchResult.getId())) {
                criterium.setCollapsed(!criterium.isCollapsed());
                graphContainer.redoLayout();
                break;
            }
        }
    }
}
