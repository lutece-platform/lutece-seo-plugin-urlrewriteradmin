--
-- Dumping data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'URLREWRITERADMIN_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('URLREWRITERADMIN_MANAGEMENT','urlrewriteradmin.adminFeature.urlrewriteradmin_management.name',1,'jsp/admin/plugins/urlrewriteradmin/ManageUrlRewriterRules.jsp','urlrewriteradmin.adminFeature.urlrewriteradmin_management.name',0,'urlrewriteradmin',NULL,NULL,NULL,4);


--
-- Dumping data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'URLREWRITERADMIN_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('URLREWRITERADMIN_MANAGEMENT',1);
