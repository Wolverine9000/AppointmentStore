/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.HashMap;

/**
 *
 * @author williamdobbs
 */
public class AutoCompleteData
{

    private final String label;
    private final String value;
    private final HashMap composers = new HashMap();

    public HashMap getClients()
    {
        return composers;
    }

    public AutoCompleteData(String _label, String _value)
    {
        super();
        this.label = _label;
        this.value = _value;
    }

    public final String getLabel()
    {
        return this.label;
    }

    public final String getValue()
    {
        return this.value;
    }
}
