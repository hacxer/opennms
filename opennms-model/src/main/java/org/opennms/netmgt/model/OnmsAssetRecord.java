package org.opennms.netmgt.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.core.style.ToStringCreator;


/** 
 * Represents the asset information for a node.
 * 
 * @hibernate.class table="assets"
 *     
 */
public class OnmsAssetRecord implements Serializable {

    private static final long serialVersionUID = 509128305684814487L;
    
    private Integer m_id;
    
    /** identifier field */
    private String m_category = "Unspecified";

    /** identifier field */
    private String m_manufacturer;

    /** identifier field */
    private String m_vendor;

    /** identifier field */
    private String m_modelNumber;

    /** identifier field */
    private String m_serialNumber;

    /** identifier field */
    private String m_description;

    /** identifier field */
    private String m_circuitId;

    /** identifier field */
    private String m_assetNumber;

    /** identifier field */
    private String m_operatingSystem;

    /** identifier field */
    private String m_rack;

    /** identifier field */
    private String m_slot;

    /** identifier field */
    private String m_port;

    /** identifier field */
    private String m_region;

    /** identifier field */
    private String m_division;

    /** identifier field */
    private String m_department;

    /** identifier field */
    private String m_address1;

    /** identifier field */
    private String m_address2;

    /** identifier field */
    private String m_city;

    /** identifier field */
    private String m_state;

    /** identifier field */
    private String m_zip;

    /** identifier field */
    private String m_building;

    /** identifier field */
    private String m_floor;

    /** identifier field */
    private String m_room;

    /** identifier field */
    private String m_vendorPhone;

    /** identifier field */
    private String m_vendorFax;

    /** identifier field */
    private String m_vendorAssetNumber;

    /** identifier field */
    private String m_lastModifiedBy = "";

    /** identifier field */
    private Date m_lastModifiedDate = new Date();

    /** identifier field */
    private String m_dateInstalled;

    /** identifier field */
    private String m_lease;

    /** identifier field */
    private String m_leaseExpires;

    /** identifier field */
    private String m_supportPhone;

    /** identifier field */
    private String m_maintContractNumber;

    /** identifier field */
    private String m_maintContractExpiration;

    /** identifier field */
    private String m_displayCategory;

    /** identifier field */
    private String m_notifyCategory;

    /** identifier field */
    private String m_pollerCategory;

    /** identifier field */
    private String m_thresholdCategory;

    /** identifier field */
    private String m_comment;

    /** persistent field */
    private OnmsNode m_node;

    /** default constructor */
    public OnmsAssetRecord() {
    }

    /**
     * Unique identifier for snmpInterface.
     * 
     * @hibernate.id generator-class="native" column="id"
     * @hibernate.generator-param name="sequence" value="assetNxtId"
     *         
     */
    public Integer getId() {
        return m_id;
    }
    
    protected void setId(Integer id) {
        m_id = id;
    }

    /** 
     * The node this asset information belongs to.
     * 
     * @hibernate.many-to-one column="nodeId" outer-join="false" not-null="true" unique="true"
     */
    public OnmsNode getNode() {
        return m_node;
    }

    /**
     * Set the node associated with the asset record
     * 
     */
    public void setNode(OnmsNode node) {
        m_node = node;
    }



    /** 
--# category         : A broad idea of what this asset does (examples are
--#                    desktop, printer, server, infrastructure, etc.).
     *                @hibernate.property
     *                 column="category"
     *                 length="64"
     *             
     */
    public String getCategory() {
        return m_category;
    }

    public void setCategory(String category) {
        m_category = category;
    }

    /** 
--# manufacturer     : Name of the manufacturer of this asset.
     *                @hibernate.property
     *                 column="manufacturer"
     *                 length="64"
     *             
     */
    public String getManufacturer() {
        return m_manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        m_manufacturer = manufacturer;
    }

    /** 
--# vendor           : Vendor from whom this asset was purchased.
     *                @hibernate.property
     *                 column="vendor"
     *                 length="64"
     *             
     */
    public String getVendor() {
        return m_vendor;
    }

    public void setVendor(String vendor) {
        m_vendor = vendor;
    }

    /** 
--# modelNumber      : The model number of this asset.
     *                @hibernate.property
     *                 column="modelnumber"
     *                 length="64"
     *             
     */
    public String getModelNumber() {
        return m_modelNumber;
    }

    public void setModelNumber(String modelnumber) {
        m_modelNumber = modelnumber;
    }

    /** 
--# serialNumber     : The serial number of this asset.
     *                @hibernate.property
     *                 column="serialnumber"
     *                 length="64"
     *             
     */
    public String getSerialNumber() {
        return m_serialNumber;
    }

    public void setSerialNumber(String serialnumber) {
        m_serialNumber = serialnumber;
    }

    /** 
--# description      : A free-form description.
     *                @hibernate.property
     *                 column="description"
     *                 length="128"
     *             
     */
    public String getDescription() {
        return m_description;
    }

    public void setDescription(String description) {
        m_description = description;
    }

    /** 
--# circuitId        : The electrical/network circuit this asset connects to.
     *                @hibernate.property
     *                 column="circuitid"
     *                 length="64"
     *             
     */
    public String getCircuitId() {
        return m_circuitId;
    }

    public void setCircuitId(String circuitid) {
        m_circuitId = circuitid;
    }

    /** 
--# assetNumber      : A business-specified asset number.
     *                @hibernate.property
     *                 column="assetnumber"
     *                 length="64"
     *             
     */
    public String getAssetNumber() {
        return m_assetNumber;
    }

    public void setAssetNumber(String assetnumber) {
        m_assetNumber = assetnumber;
    }

    /** 
--# operatingSystem  : The operating system, if any.
     *                @hibernate.property
     *                 column="operatingsystem"
     *                 length="64"
     *             
     */
    public String getOperatingSystem() {
        return m_operatingSystem;
    }

    public void setOperatingSystem(String operatingsystem) {
        m_operatingSystem = operatingsystem;
    }

    /** 
--# rack             : For servers, the rack it is installed in.
     *                @hibernate.property
     *                 column="rack"
     *                 length="64"
     *             
     */
    public String getRack() {
        return m_rack;
    }

    public void setRack(String rack) {
        m_rack = rack;
    }

    /** 
--# slot             : For servers, the slot in the rack it is installed in.
     *                @hibernate.property
     *                 column="slot"
     *                 length="64"
     *             
     */
    public String getSlot() {
        return m_slot;
    }

    public void setSlot(String slot) {
        m_slot = slot;
    }

    /** 
--# port             : For servers, the port in the slot it is installed in.
     *                @hibernate.property
     *                 column="port"
     *                 length="64"
     *             
     */
    public String getPort() {
        return m_port;
    }

    public void setPort(String port) {
        m_port = port;
    }

    /** 
--# region           : A broad geographical or organizational area.
     *                @hibernate.property
     *                 column="region"
     *                 length="64"
     *             
     */
    public String getRegion() {
        return m_region;
    }

    public void setRegion(String region) {
        m_region = region;
    }

    /** 
--# division         : A broad geographical or organizational area.
     *                @hibernate.property
     *                 column="division"
     *                 length="64"
     *             
     */
    public String getDivision() {
        return m_division;
    }

    public void setDivision(String division) {
        m_division = division;
    }

    /** 
--# department       : The department this asset belongs to.
     *                @hibernate.property
     *                 column="department"
     *                 length="64"
     *             
     */
    public String getDepartment() {
        return m_department;
    }

    public void setDepartment(String department) {
        m_department = department;
    }

    /** 
--# address1         : Address of geographical location of asset, line 1.
     *                @hibernate.property
     *                 column="address1"
     *                 length="256"
     *             
     */
    public String getAddress1() {
        return m_address1;
    }

    public void setAddress1(String address1) {
        m_address1 = address1;
    }

    /** 
--# address2         : Address of geographical location of asset, line 2.
     *                @hibernate.property
     *                 column="address2"
     *                 length="256"
     *             
     */
    public String getAddress2() {
        return m_address2;
    }

    public void setAddress2(String address2) {
        m_address2 = address2;
    }

    /** 
--# city             : The city where this asset resides.
     *                @hibernate.property
     *                 column="city"
     *                 length="64"
     *             
     */
    public String getCity() {
        return m_city;
    }

    public void setCity(String city) {
        m_city = city;
    }

    /** 
--# state            : The state where this asset resides.
     *                @hibernate.property
     *                 column="state"
     *                 length="64"
     *             
     */
    public String getState() {
        return m_state;
    }

    public void setState(String state) {
        m_state = state;
    }

    /** 
--# zip              : The zip code where this asset resides.
     *                @hibernate.property
     *                 column="zip"
     *                 length="64"
     *             
     */
    public String getZip() {
        return m_zip;
    }

    public void setZip(String zip) {
        m_zip = zip;
    }

    /** 
--# building         : The building where this asset resides.
     *                @hibernate.property
     *                 column="building"
     *                 length="64"
     *             
     */
    public String getBuilding() {
        return m_building;
    }

    public void setBuilding(String building) {
        m_building = building;
    }

    /** 
--# floor            : The floor of the building where this asset resides.
     *                @hibernate.property
     *                 column="floor"
     *                 length="64"
     *             
     */
    public String getFloor() {
        return m_floor;
    }

    public void setFloor(String floor) {
        m_floor = floor;
    }

    /** 
--# room             : The room where this asset resides.
     *                @hibernate.property
     *                 column="room"
     *                 length="64"
     *             
     */
    public String getRoom() {
        return m_room;
    }

    public void setRoom(String room) {
        m_room = room;
    }

    /** 
--# vendorPhone      : A contact number for the vendor.
     *                @hibernate.property
     *                 column="vendorphone"
     *                 length="64"
     *             
     */
    public String getVendorPhone() {
        return m_vendorPhone;
    }

    public void setVendorPhone(String vendorphone) {
        m_vendorPhone = vendorphone;
    }

    /** 
--# vendorFax        : A fax number for the vendor.
     *                @hibernate.property
     *                 column="vendorfax"
     *                 length="64"
     *             
     */
    public String getVendorFax() {
        return m_vendorFax;
    }

    public void setVendorFax(String vendorfax) {
        m_vendorFax = vendorfax;
    }

    /** 
     *                @hibernate.property
     *                 column="vendorassetnumber"
     *                 length="64"
     *             
     */
    public String getVendorAssetNumber() {
        return m_vendorAssetNumber;
    }

    public void setVendorAssetNumber(String vendorassetnumber) {
        m_vendorAssetNumber = vendorassetnumber;
    }

    /** 
--# userLastModified : The last user who modified this record.
     *                @hibernate.property
     *                 column="userlastmodified"
     *                 length="20"
     *             
     */
    public String getLastModifiedBy() {
        return m_lastModifiedBy;
    }

    public void setLastModifiedBy(String userlastmodified) {
        m_lastModifiedBy = userlastmodified;
    }

    /** 
--# lastModifiedDate : The last time this record was modified.
     *                @hibernate.property
     *                 column="lastmodifieddate"
     *                 length="8"
     *             
     */
    public Date getLastModifiedDate() {
        return m_lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastmodifieddate) {
        m_lastModifiedDate = lastmodifieddate;
    }

    /** 
--# dateInstalled    : The date the asset was installed.
     *                @hibernate.property
     *                 column="dateinstalled"
     *                 length="64"
     *             
     */
    public String getDateInstalled() {
        return m_dateInstalled;
    }

    public void setDateInstalled(String dateinstalled) {
        m_dateInstalled = dateinstalled;
    }

    /** 
--# lease            : The lease number of this asset.
     *                @hibernate.property
     *                 column="lease"
     *                 length="64"
     *             
     */
    public String getLease() {
        return m_lease;
    }

    public void setLease(String lease) {
        m_lease = lease;
    }

    /** 
--# leaseExpires     : The date the lease expires for this asset.
     *                @hibernate.property
     *                 column="leaseexpires"
     *                 length="64"
     *             
     */
    public String getLeaseExpires() {
        return m_leaseExpires;
    }

    public void setLeaseExpires(String leaseexpires) {
        m_leaseExpires = leaseexpires;
    }

    /** 
--# supportPhone     : A support phone number for this asset.
     *                @hibernate.property
     *                 column="supportphone"
     *                 length="64"
     *             
     */
    public String getSupportPhone() {
        return m_supportPhone;
    }

    public void setSupportPhone(String supportphone) {
        m_supportPhone = supportphone;
    }

    /** 
--# maintContract    : The maintenance contract number for this asset.
     *                @hibernate.property
     *                 column="maintcontract"
     *                 length="64"
     *             
     */
    public String getMaintContractNumber() {
        return m_maintContractNumber;
    }

    public void setMaintContractNumber(String maintcontract) {
        m_maintContractNumber = maintcontract;
    }

    /** 
     *                @hibernate.property
     *                 column="maintcontractexpires"
     *                 length="64"
     *             
     */
    public String getMaintContractExpiration() {
        return m_maintContractExpiration;
    }

    public void setMaintContractExpiration(String maintcontractexpires) {
        m_maintContractExpiration = maintcontractexpires;
    }

    /** 
     *                @hibernate.property
     *                 column="displaycategory"
     *                 length="64"
     *             
     */
    public String getDisplayCategory() {
        return m_displayCategory;
    }

    public void setDisplayCategory(String displaycategory) {
        m_displayCategory = displaycategory;
    }

    /** 
     *                @hibernate.property
     *                 column="notifycategory"
     *                 length="64"
     *             
     */
    public String getNotifyCategory() {
        return m_notifyCategory;
    }

    public void setNotifyCategory(String notifycategory) {
        m_notifyCategory = notifycategory;
    }

    /** 
     *                @hibernate.property
     *                 column="pollercategory"
     *                 length="64"
     *             
     */
    public String getPollerCategory() {
        return m_pollerCategory;
    }

    public void setPollerCategory(String pollercategory) {
        m_pollerCategory = pollercategory;
    }

    /** 
     *                @hibernate.property
     *                 column="thresholdcategory"
     *                 length="64"
     *             
     */
    public String getThresholdCategory() {
        return m_thresholdCategory;
    }

    public void setThresholdCategory(String thresholdcategory) {
        m_thresholdCategory = thresholdcategory;
    }

    /** 
     *                @hibernate.property
     *                 column="comment"
     *                 length="1024"
     *             
     */
    public String getComment() {
        return m_comment;
    }

    public void setComment(String comment) {
        m_comment = comment;
    }
    
    public Serializable getSerializableId() {
        return getId();
    }

    public String toString() {
        return new ToStringCreator(this)
            .append("category", getCategory())
            .append("manufacturer", getManufacturer())
            .append("vendor", getVendor())
            .append("modelnumber", getModelNumber())
            .append("serialnumber", getSerialNumber())
            .append("description", getDescription())
            .append("circuitid", getCircuitId())
            .append("assetnumber", getAssetNumber())
            .append("operatingsystem", getOperatingSystem())
            .append("rack", getRack())
            .append("slot", getSlot())
            .append("port", getPort())
            .append("region", getRegion())
            .append("division", getDivision())
            .append("department", getDepartment())
            .append("address1", getAddress1())
            .append("address2", getAddress2())
            .append("city", getCity())
            .append("state", getState())
            .append("zip", getZip())
            .append("building", getBuilding())
            .append("floor", getFloor())
            .append("room", getRoom())
            .append("vendorphone", getVendorPhone())
            .append("vendorfax", getVendorFax())
            .append("vendorassetnumber", getVendorAssetNumber())
            .append("userlastmodified", getLastModifiedBy())
            .append("lastmodifieddate", getLastModifiedDate())
            .append("dateinstalled", getDateInstalled())
            .append("lease", getLease())
            .append("leaseexpires", getLeaseExpires())
            .append("supportphone", getSupportPhone())
            .append("maintcontract", getMaintContractNumber())
            .append("maintcontractexpires", getMaintContractExpiration())
            .append("displaycategory", getDisplayCategory())
            .append("notifycategory", getNotifyCategory())
            .append("pollercategory", getPollerCategory())
            .append("thresholdcategory", getThresholdCategory())
            .append("comment", getComment())
            .toString();
    }

}
