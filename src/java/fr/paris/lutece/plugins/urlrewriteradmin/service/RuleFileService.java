/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.urlrewriteradmin.service;

import fr.paris.lutece.plugins.urlrewriteradmin.business.UrlRewriterRule;
import fr.paris.lutece.plugins.urlrewriteradmin.business.UrlRewriterRuleHome;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;


/**
 * RuleFileService
 */
public final class RuleFileService
{
    private static final String TEMPLATE_FILE = "/admin/plugins/urlrewriteradmin/urlrewrite.xml";
    private static final String MARK_RULES_LIST = "rules_list";
    private static final String PROPERTY_FILE = "urlrewriteradmin.configFilePath";

    /**
     * Private constructor
     */
    private RuleFileService(  )
    {
    }

    /**
     * Generate the rule file
     * @throws java.io.IOException If an error occurs
     */
    public static void generateFile(  ) throws IOException
    {
        String strFilePath = AppPathService.getWebAppPath(  ) + AppPropertiesService.getProperty( PROPERTY_FILE );
        File file = new File( strFilePath );
        FileUtils.writeStringToFile( file, generateFileContent(  ) );
    }

    /**
     * Generate the rule file content
     * @return The file content
     */
    private static String generateFileContent(  )
    {
        HashMap model = new HashMap(  );
        Collection<UrlRewriterRule> listRules = UrlRewriterRuleHome.findAll(  );
        model.put( MARK_RULES_LIST, listRules );

        HtmlTemplate t = AppTemplateService.getTemplate( TEMPLATE_FILE, Locale.getDefault(  ), model );

        return t.getHtml(  );
    }
}
