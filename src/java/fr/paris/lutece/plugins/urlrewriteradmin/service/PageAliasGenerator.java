/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
import fr.paris.lutece.portal.business.page.Page;
import fr.paris.lutece.portal.business.page.PageHome;
import fr.paris.lutece.portal.service.portal.PortalService;

import java.util.List;


/**
 * Page Alias Generator
 */
public class PageAliasGenerator implements AliasGenerator
{
    private static final String GENERATOR_NAME = "Page Alias Generator";
    private static final String TECHNICAL_URL = "/jsp/site/Portal.jsp?page_id=";
    private static final String SLASH = "/";
    private static final String EMPTY = "";

    /**
     * {@inheritDoc }
     */
    @Override
    public String getName(  )
    {
        return GENERATOR_NAME;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String generate( List<UrlRewriterRule> list, AliasGeneratorOptions options )
    {
        StringBuilder sbLog = new StringBuilder(  );

        String strPath = EMPTY;
        findPage( list, PortalService.getRootPageId(  ), strPath, sbLog, options );

        return sbLog.toString(  );
    }

    /**
     * Fill recursively the rule list
     * @param list The rules list
     * @param nPage The current page id
     * @param strPath The page's path
     * @param sbLog Logs
     * @param options  Options
     */
    private void findPage( List<UrlRewriterRule> list, int nPage, String strPath, StringBuilder sbLog,
        AliasGeneratorOptions options )
    {
        Page page = PageHome.findByPrimaryKey( nPage );
        UrlRewriterRule rule = new UrlRewriterRule(  );
        String strAlias = AliasGeneratorUtils.convertToAlias( page.getName(  ) );
        String strChildPath = ( strPath.equals( EMPTY ) ) ? SLASH : ( strPath + strAlias + SLASH );
        String strRuleFrom = ( options.isAddPath(  ) ) ? ( strPath + strAlias ) : ( SLASH + strAlias );
        strRuleFrom = ( strPath.equals( EMPTY ) ) ? ( SLASH + strAlias ) : strRuleFrom;
        rule.setRuleFrom( strRuleFrom );
        rule.setRuleTo( TECHNICAL_URL + page.getId(  ) );
        list.add( rule );

        for ( Page childPage : PageHome.getChildPages( nPage ) )
        {
            findPage( list, childPage.getId(  ), strChildPath, sbLog, options );
        }
    }
}
