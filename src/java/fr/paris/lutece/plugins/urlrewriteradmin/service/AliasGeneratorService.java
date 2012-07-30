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
import fr.paris.lutece.plugins.urlrewriteradmin.business.UrlRewriterRuleHome;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Alias Generator Service
 */
public class AliasGeneratorService
{
    private static final String SUFFIX_HTML = ".html";
    private static final int NOT_FOUND = -1;
    private static List<AliasGenerator> _listGenerators = new ArrayList<AliasGenerator>(  );
    private static AliasGeneratorService _singleton;

    /**
     * private constructore
     */
    private AliasGeneratorService(  )
    {
    }

    /**
     * Return the unique instance
     * @return The instance
     */
    public static synchronized AliasGeneratorService instance(  )
    {
        if ( _singleton == null )
        {
            _singleton = new AliasGeneratorService(  );

            _listGenerators = SpringContextService.getBeansOfType( AliasGenerator.class );
        }

        return _singleton;
    }

    /**
     * Generate Alias rules
     * @param options Options
     */
    public void generate( AliasGeneratorOptions options )
    {
        Collection<UrlRewriterRule> listExisting = UrlRewriterRuleHome.findAll(  );

        List<UrlRewriterRule> listRules = new ArrayList<UrlRewriterRule>(  );

        for ( AliasGenerator generator : _listGenerators )
        {
            generator.generate( listRules, options );
        }

        processRuleList( listRules, listExisting, options );
    }

    /**
     * Gets the generators list
     * @return The generators list
     */
    public List<AliasGenerator> getGenerators(  )
    {
        return _listGenerators;
    }

    private void processRuleList( List<UrlRewriterRule> listRules, Collection<UrlRewriterRule> listExisting,
        AliasGeneratorOptions options )
    {
        AppLogService.info( "Processing Url rewriting Alias rules" );
        AppLogService.info( "* Option Force update existing rules : " + ( options.isForceUpdate(  ) ? "on" : "off" ) );
        AppLogService.info( "* Option Add path : " + ( options.isAddPath(  ) ? "on" : "off" ) );
        AppLogService.info( "* Option Html suffix : " + ( options.isHtmlSuffix(  ) ? "on" : "off" ) );

        for ( UrlRewriterRule rule : listRules )
        {
            if ( options.isHtmlSuffix(  ) )
            {
                rule.setRuleFrom( rule.getRuleFrom(  ) + SUFFIX_HTML );
            }

            int nExistingRuleId = getExistingRuleId( listExisting, rule );

            if ( nExistingRuleId != NOT_FOUND )
            {
                if ( options.isForceUpdate(  ) )
                {
                    // update the existing alias
                    rule.setIdRule( nExistingRuleId );
                    UrlRewriterRuleHome.update( rule );
                    AppLogService.info( "Updated : " + rule.getRuleFrom(  ) + " -> " + rule.getRuleTo(  ) );
                }
                else
                {
                    AppLogService.info( "Ignored : " + rule.getRuleFrom(  ) + " -> " + rule.getRuleTo(  ) );
                }
            }
            else
            {
                // create a new alias
                UrlRewriterRuleHome.create( rule );
                AppLogService.info( "Created : " + rule.getRuleFrom(  ) + " -> " + rule.getRuleTo(  ) );
            }
        }
    }

    private int getExistingRuleId( Collection<UrlRewriterRule> listExisting, UrlRewriterRule rule )
    {
        for ( UrlRewriterRule r : listExisting )
        {
            if ( r.getRuleTo(  ).equals( rule.getRuleTo(  ) ) )
            {
                return r.getIdRule(  );
            }
        }

        return NOT_FOUND;
    }
}
