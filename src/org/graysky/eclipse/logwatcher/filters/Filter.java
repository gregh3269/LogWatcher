package org.graysky.eclipse.logwatcher.filters;

import java.util.Iterator;
import java.util.Vector;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.eclipse.swt.custom.LineStyleEvent;

public class Filter 
{
	private String			m_pattern		= null;
	private boolean		m_caseSensitive	= false;
	
	// Today, only one action is supported per filter by the GUI.
	private Vector			m_actions		= new Vector();
	private boolean		m_contains		= true;
	private Pattern		m_regexp		= null;
	private Perl5Matcher	m_matcher		= new Perl5Matcher();
	
	/**
	 * Test if the given string is matched by this filter.
	 */
	public boolean matches(String str)
	{
		boolean match = m_matcher.contains(str, m_regexp);
		if (m_contains) {
			return match;
		}
		else {
			return !match;
		}
	}

	/**
	 * Take the specified actions for the given string, which is assumed to have
	 * matched the filter.
	 */
	public void handleViewerMatch(LineStyleEvent event)
	{
		for (Iterator iter = m_actions.iterator(); iter.hasNext();) {
            FilterAction action = (FilterAction) iter.next();
            action.doViewerAction(event);
        }
	}
	
	public String handleWatcherMatch(String line)
	{
		// @@@ For now there's only one action... this needs to change.
		for (Iterator iter = m_actions.iterator(); iter.hasNext();) {
            FilterAction action = (FilterAction) iter.next();
            return action.doWatcherAction(line);
        }
        
        return line;
	}
	
	public void addAction(FilterAction action)
	{
		m_actions.add(action);	
	}

	public String getDescription()
	{
		FilterAction a = (FilterAction) m_actions.get(0);
		String contains = (m_contains ? "contains" : "does not contain");
		if (a != null) {
			return a.getDescription() + " when text "  + contains + " \"" + m_pattern + "\"";
		}
		else {
			return "No action specified for filter";
		}
	}	
	
	public String getPattern()
	{
		return m_pattern;
	}

	public void setPattern(String pattern, boolean caseSensitive) throws MalformedPatternException
	{
		m_pattern = pattern;
		m_caseSensitive = caseSensitive;
		Perl5Compiler compiler = new Perl5Compiler();
		if (!m_caseSensitive) {
			m_regexp = compiler.compile(m_pattern, Perl5Compiler.CASE_INSENSITIVE_MASK);
		}
		else {
			m_regexp = compiler.compile(m_pattern);
		}
	}

    public boolean getContains()
    {
        return m_contains;
    }

	public void dispose()
	{
		for (Iterator iter = m_actions.iterator(); iter.hasNext();) {
            FilterAction element = (FilterAction) iter.next();
            element.dispose();
        }	
	}

    public void setContains(boolean contains)
    {
        m_contains = contains;
    }

	public boolean isCaseSensitive()
	{
		return m_caseSensitive;
	}
}
