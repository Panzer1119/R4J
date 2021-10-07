package no.stelar7.api.r4j.pojo.tft.league;

import no.stelar7.api.r4j.basic.constants.types.lol.*;

import java.io.Serializable;
import java.util.*;

public class TFTLeagueList implements Serializable
{
    private static final long serialVersionUID = 3779955487138585327L;
    
    private List<TFTLeagueEntry> entries;
    private String               name;
    private GameQueueType        queue;
    private TierType             tier;
    private String               leagueId;
    
    
    /**
     * Takes in a summonerId, and returns the TierDivisionType created from it.
     * Returns UNRANKED if the id is not found
     *
     * @param id the id to use for construction
     * @return TierDivisionType
     */
    public TierDivisionType getTierDivisionType(String id)
    {
        TFTLeagueEntry pos = getLeagueEntry(id);
        
        return pos == null ? TierDivisionType.UNRANKED : pos.getTierDivisionType(tier);
    }
    
    /**
     * Takes in a summonerId, and returns the TFTLeagueEntry found in the list.
     * Returns null if not found
     *
     * @param id the Tier to use for construction
     * @return LeagueEntry
     */
    public TFTLeagueEntry getLeagueEntry(String id)
    {
        return entries.stream().filter(s -> s.getSummonerId().equals(id)).findFirst().orElse(null);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        
        TFTLeagueList that = (TFTLeagueList) o;
        
        if (!Objects.equals(entries, that.entries))
        {
            return false;
        }
        if (!Objects.equals(name, that.name))
        {
            return false;
        }
        if (queue != that.queue)
        {
            return false;
        }
        if (tier != that.tier)
        {
            return false;
        }
        return Objects.equals(leagueId, that.leagueId);
    }
    
    @Override
    public int hashCode()
    {
        int result = entries != null ? entries.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (queue != null ? queue.hashCode() : 0);
        result = 31 * result + (tier != null ? tier.hashCode() : 0);
        result = 31 * result + (leagueId != null ? leagueId.hashCode() : 0);
        return result;
    }
    
    /**
     * The requested league entries.
     *
     * @return the entries
     */
    public List<TFTLeagueEntry> getEntries()
    {
        return this.entries;
    }
    
    /**
     * This name is an internal place-holder name only. Display and localization of names in the game client are handled client-side.
     *
     * @return the name
     */
    public String getLeagueName()
    {
        return this.name;
    }
    
    /**
     * ID of the league
     *
     * @return String
     */
    public String getLeagueId()
    {
        return leagueId;
    }
    
    
    /**
     * The league's queue type. (Legal values: TEAMFIGHT_TACTICS_RANKED, TEAMFIGHT_TACTICS_HYPER_ROLL)
     *
     * @return the queue
     */
    public GameQueueType getQueueType()
    {
        return this.queue;
    }
    
    /**
     * The league's tier. (Legal values: CHALLENGER, MASTER, DIAMOND, PLATINUM, GOLD, SILVER, BRONZE, ORANGE, PURPLE, BLUE, GREEN, GRAY)
     *
     * @return the tier
     */
    public TierType getTier()
    {
        return this.tier;
    }
    
    @Override
    public String toString()
    {
        return "TFTLeagueList{" +
               "entries=" + entries +
               ", name='" + name + '\'' +
               ", queue=" + queue +
               ", tier=" + tier +
               ", leagueId='" + leagueId + '\'' +
               '}';
    }
}