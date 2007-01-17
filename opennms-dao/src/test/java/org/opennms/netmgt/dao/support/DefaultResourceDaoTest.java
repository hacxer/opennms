//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
package org.opennms.netmgt.dao.support;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
        
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.config.CollectdConfigFactory;
import org.opennms.netmgt.config.DataCollectionConfig;
import org.opennms.netmgt.config.datacollection.ResourceType;
import org.opennms.netmgt.dao.GraphDao;
import org.opennms.netmgt.dao.LocationMonitorDao;
import org.opennms.netmgt.dao.NodeDao;
import org.opennms.netmgt.dao.support.DefaultResourceDao;
import org.opennms.netmgt.model.OnmsResource;
import org.opennms.netmgt.model.LocationMonitorIpInterface;
import org.opennms.netmgt.model.OnmsIpInterface;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.rrd.RrdUtils;
import org.opennms.test.ConfigurationTestUtils;
import org.opennms.test.FileAnticipator;
import org.opennms.test.ThrowableAnticipator;
import org.springframework.orm.ObjectRetrievalFailureException;

import junit.framework.TestCase;

public class DefaultResourceDaoTest extends TestCase {
    private NodeDao m_nodeDao;
    private LocationMonitorDao m_locationMonitorDao;
    private CollectdConfigFactory m_collectdConfig;
    private DataCollectionConfig m_dataCollectionConfig;
    private DefaultResourceDao m_resourceDao;
    private GraphDao m_graphDao;
    
    private FileAnticipator m_fileAnticipator;
    
    @Override
    protected void setUp() throws Exception {
        setUpCollectdConfigFactory();
        
        m_fileAnticipator = new FileAnticipator();
        m_nodeDao = createMock(NodeDao.class);
        m_locationMonitorDao = createMock(LocationMonitorDao.class);
        m_dataCollectionConfig = createMock(DataCollectionConfig.class);
        m_graphDao = createMock(GraphDao.class);

        m_resourceDao = new DefaultResourceDao();
        m_resourceDao.setNodeDao(m_nodeDao);
        m_resourceDao.setLocationMonitorDao(m_locationMonitorDao);
        m_resourceDao.setCollectdConfig(m_collectdConfig);
        m_resourceDao.setRrdDirectory(m_fileAnticipator.getTempDir());
        m_resourceDao.setDataCollectionConfig(m_dataCollectionConfig);
        
        RrdUtils.initialize();
        
        expect(m_dataCollectionConfig.getConfiguredResourceTypes()).andReturn(new HashMap<String, ResourceType>());
        
        replayAll();
        m_resourceDao.afterPropertiesSet();
        verifyAll();
        resetAll();
    }
    
    @Override
    protected void tearDown() {
        m_fileAnticipator.tearDown();
    }
    
    private void replayAll() {
        replay(m_nodeDao);
        replay(m_locationMonitorDao);
        replay(m_graphDao);
        replay(m_dataCollectionConfig);
    }
    
    private void verifyAll() {
        verify(m_nodeDao);
        verify(m_locationMonitorDao);
        verify(m_graphDao);
        verify(m_dataCollectionConfig);
    }
    
    private void resetAll() {
        reset(m_nodeDao);
        reset(m_locationMonitorDao);
        reset(m_graphDao);
        reset(m_dataCollectionConfig);
    }

    private void setUpCollectdConfigFactory() throws MarshalException, ValidationException, IOException {
        Reader rdr = ConfigurationTestUtils.getReaderForResource(this, "/collectdconfiguration-testdata.xml");
        m_collectdConfig = new CollectdConfigFactory(rdr, "localhost", false);
        rdr.close();
    }

    public void testGetResourceByIdNewEmpty() {
        replayAll();
        m_resourceDao.getResourceById("");
        verifyAll();
    }
   
    
    public void testGetResourceByIdNewTopLevelOnly() {
        OnmsNode node = new OnmsNode();
        node.setId(1);
        expect(m_nodeDao.get(node.getId())).andReturn(node);
        
        replayAll();
        OnmsResource resource = m_resourceDao.getResourceById("node[1]");
        verifyAll();
        
        assertNotNull("resource should not be null", resource);
    }

    public void testGetResourceByIdNewTwoLevel() throws Exception {
        OnmsNode node = new OnmsNode();
        node.setId(1);
        OnmsIpInterface ip = new OnmsIpInterface();
        ip.setIpAddress("192.168.1.1");
        node.addIpInterface(ip);
        expect(m_nodeDao.get(node.getId())).andReturn(node).times(3);

        Collection<LocationMonitorIpInterface> locMons = new HashSet<LocationMonitorIpInterface>();
        expect(m_locationMonitorDao.findStatusChangesForNodeForUniqueMonitorAndInterface(1)).andReturn(locMons).times(1);
        
        File response = m_fileAnticipator.tempDir("response");
        File ipDir = m_fileAnticipator.tempDir(response, "192.168.1.1");
        m_fileAnticipator.tempFile(ipDir, "icmp" + RrdUtils.getExtension());
                
        replayAll();
        OnmsResource resource = m_resourceDao.getResourceById("node[1].responseTime[192.168.1.1]");
        verifyAll();
        
        assertNotNull("resource should not be null", resource);
    }
    
    public void testGetTopLevelResourceNodeExists() {
        OnmsNode node = new OnmsNode();
        node.setId(1);
        expect(m_nodeDao.get(node.getId())).andReturn(node);

        replayAll();
        OnmsResource resource = m_resourceDao.getTopLevelResource("node", "1");
        verifyAll();
        
        assertNotNull("Resource should not be null", resource);
    }
    
    public void testGetTopLevelResourceNodeDoesNotExist() {
        expect(m_nodeDao.get(2)).andReturn(null);
        ThrowableAnticipator ta = new ThrowableAnticipator();
        ta.anticipate(new ObjectRetrievalFailureException(OnmsNode.class, "2", "Top-level resource of resource type node could not be found: 2", null));

        replayAll();
        try {
            m_resourceDao.getTopLevelResource("node", "2");
        } catch (Throwable t) {
            ta.throwableReceived(t);
        }
        verifyAll();
        ta.verifyAnticipated();
    }
    
    public void testGetTopLevelResourceDomainExists() throws IOException {
        File snmp = m_fileAnticipator.tempDir("snmp");
        File domain = m_fileAnticipator.tempDir(snmp, "example1");
        File intf = m_fileAnticipator.tempDir(domain, "server1");
        m_fileAnticipator.tempFile(intf, "ifInOctects" + RrdUtils.getExtension());
        
        
        replayAll();
        OnmsResource resource = m_resourceDao.getTopLevelResource("domain", "example1");
        verifyAll();
        
        assertNotNull("Resource should not be null", resource);
    }
    
    public void testGetTopLevelResourceDomainDoesNotExistInCollectdConfig() {
        ThrowableAnticipator ta = new ThrowableAnticipator();
        ta.anticipate(new ObjectRetrievalFailureException(OnmsResource.class, "bogus", "Domain not found as a configured domain or package in collectd configuration", null));
        
        replayAll();
        try {
            m_resourceDao.getTopLevelResource("domain", "bogus");
        } catch (Throwable t) {
            ta.throwableReceived(t);
        }
        verifyAll();
        ta.verifyAnticipated();
    }

    // We don't need to test everything that could cause the filter to fail... that's the job of a filter test case
    public void testGetTopLevelResourceDomainDoesNotExistNoInterfaceDirectories() throws IOException {
        File snmp = m_fileAnticipator.tempDir("snmp");
        m_fileAnticipator.tempDir(snmp, "example1");

        ThrowableAnticipator ta = new ThrowableAnticipator();
        File dir = new File(new File(m_fileAnticipator.getTempDir(), "snmp"), "example1");
        ta.anticipate(new ObjectRetrievalFailureException(OnmsResource.class, "example1", "Domain not found due to domain RRD directory not matching the domain directory filter: " + dir.getAbsolutePath(), null));

        replayAll();
        try {
            m_resourceDao.getTopLevelResource("domain", "example1");
        } catch (Throwable t) {
            ta.throwableReceived(t);
        }
        verifyAll();
        ta.verifyAnticipated();
    }

    public void testGetTopLevelResourceWithInvalidResourceType() {
        ThrowableAnticipator ta = new ThrowableAnticipator();
        ta.anticipate(new ObjectRetrievalFailureException("Top-level resource type of 'bogus' is unknown", "bogus"));

        replayAll();
        try {
            m_resourceDao.getTopLevelResource("bogus", "");
        } catch (Throwable t) {
            ta.throwableReceived(t);
        }
        verifyAll();
        ta.verifyAnticipated();
    }
    
    public void testGetResourceDomainInterfaceExists() throws IOException {
        File snmp = m_fileAnticipator.tempDir("snmp");
        File domain = m_fileAnticipator.tempDir(snmp, "example1");
        File intf = m_fileAnticipator.tempDir(domain, "server1");
        m_fileAnticipator.tempFile(intf, "ifInOctects" + RrdUtils.getExtension());
        
        String resourceId = OnmsResource.createResourceId("domain", "example1", "interfaceSnmp", "server1");
        
        replayAll();
        OnmsResource resource = m_resourceDao.getResourceById(resourceId);
        verifyAll();
        
        assertNotNull("Resource should not be null", resource);
    }
    
    public void testFindNodeResourcesWithResponseTime() throws Exception {
        List<OnmsNode> nodes = new LinkedList<OnmsNode>();
        OnmsNode node = new OnmsNode();
        node.setId(1);
        OnmsIpInterface ip = new OnmsIpInterface();
        ip.setIpAddress("192.168.1.1");
        node.addIpInterface(ip);
        nodes.add(node);
        
        expect(m_nodeDao.findAll()).andReturn(nodes);
        

        File response = m_fileAnticipator.tempDir("response");
        File ipDir = m_fileAnticipator.tempDir(response, "192.168.1.1");
        m_fileAnticipator.tempFile(ipDir, "icmp" + RrdUtils.getExtension());
        
        replayAll();
        List<OnmsResource> resources = m_resourceDao.findNodeResources();
        verifyAll();
        
        assertNotNull("resource list should not be null", resources);
        assertEquals("resource list size", 1, resources.size());
    }

    // XXX this is a false positive match because there isn't an entry in the DB for this distributed data
    public void testFindNodeResourcesWithDistributedResponseTime() throws Exception {
        List<OnmsNode> nodes = new LinkedList<OnmsNode>();
        OnmsNode node = new OnmsNode();
        node.setId(1);
        OnmsIpInterface ip = new OnmsIpInterface();
        ip.setIpAddress("192.168.1.1");
        node.addIpInterface(ip);
        nodes.add(node);
        
        expect(m_nodeDao.findAll()).andReturn(nodes);

        File response = m_fileAnticipator.tempDir("response");
        File distributed = m_fileAnticipator.tempDir(response, "distributed");
        File monitor = m_fileAnticipator.tempDir(distributed, "1");
        File ipDir = m_fileAnticipator.tempDir(monitor, "192.168.1.1");
        m_fileAnticipator.tempFile(ipDir, "icmp" + RrdUtils.getExtension());
        
        replayAll();
        List<OnmsResource> resources = m_resourceDao.findNodeResources();
        verifyAll();
        
        assertNotNull("resource list should not be null", resources);
        assertEquals("resource list size", 1, resources.size());
    }

    public void testFindNodeResourcesWithNodeSnmp() throws Exception {
        List<OnmsNode> nodes = new LinkedList<OnmsNode>();
        OnmsNode node = new OnmsNode();
        node.setId(1);
        OnmsIpInterface ip = new OnmsIpInterface();
        ip.setIpAddress("192.168.1.1");
        node.addIpInterface(ip);
        nodes.add(node);
        
        expect(m_nodeDao.findAll()).andReturn(nodes);
        

        File snmp = m_fileAnticipator.tempDir("snmp");
        File nodeDir = m_fileAnticipator.tempDir(snmp, "1");
        m_fileAnticipator.tempFile(nodeDir, "foo" + RrdUtils.getExtension());
        
        replayAll();
        List<OnmsResource> resources = m_resourceDao.findNodeResources();
        verifyAll();
        
        assertNotNull("resource list should not be null", resources);
        assertEquals("resource list size", 1, resources.size());
    }


    public void testFindNodeResourcesWithNodeInterface() throws Exception {
        List<OnmsNode> nodes = new LinkedList<OnmsNode>();
        OnmsNode node = new OnmsNode();
        node.setId(1);
        OnmsIpInterface ip = new OnmsIpInterface();
        ip.setIpAddress("192.168.1.1");
        node.addIpInterface(ip);
        nodes.add(node);
        
        expect(m_nodeDao.findAll()).andReturn(nodes);
        

        File snmp = m_fileAnticipator.tempDir("snmp");
        File nodeDir = m_fileAnticipator.tempDir(snmp, "1");
        File intfDir = m_fileAnticipator.tempDir(nodeDir, "eth0");
        m_fileAnticipator.tempFile(intfDir, "foo" + RrdUtils.getExtension());
        
        replayAll();
        List<OnmsResource> resources = m_resourceDao.findNodeResources();
        verifyAll();
        
        assertNotNull("resource list should not be null", resources);
        assertEquals("resource list size", 1, resources.size());
    }
}
