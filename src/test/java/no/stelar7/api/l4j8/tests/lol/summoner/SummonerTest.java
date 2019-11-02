package no.stelar7.api.l4j8.tests.lol.summoner;


import no.stelar7.api.l4j8.basic.cache.impl.*;
import no.stelar7.api.l4j8.basic.calling.DataCall;
import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.basic.utils.SummonerCrawler;
import no.stelar7.api.l4j8.impl.L4J8;
import no.stelar7.api.l4j8.impl.lol.builders.spectator.SpectatorBuilder;
import no.stelar7.api.l4j8.impl.lol.builders.summoner.SummonerBuilder;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;
import no.stelar7.api.l4j8.tests.SecretFile;
import org.junit.*;

import java.util.function.Consumer;

public class SummonerTest
{
    
    private final Consumer<Summoner> doAssertions = (final Summoner value) ->
    {
        Assert.assertNotNull("Summoner name is NULL", value.getName());
        Assert.assertNotEquals("Summoner profile icon is NULL", value.getProfileIconId(), 0);
        Assert.assertNotEquals("Summoner revision date is NULL", value.getRevisionDate(), 0L);
        Assert.assertNotEquals("Summoner level is NULL", value.getSummonerLevel(), 0);
        Assert.assertNotNull("Summoner revison date DATE is NULL", value.getRevisionDateAsDate());
        Assert.assertNotNull("Summoner PUUID is null", value.getPUUID());
    };
    
    final L4J8 l4j8 = new L4J8(SecretFile.CREDS);
    
    @Before
    public void before()
    {
        DataCall.setCacheProvider(new TieredCacheProvider(new MemoryCacheProvider(), new FileSystemCacheProvider()));
    }
    
    @Test
    public void testById()
    {
        /*
        Summoner ids have been removed from the spectator endpoint, so this test wont work anymore...
        
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerId();
        Summoner s  = new SummonerBuilder().withPlatform(Platform.EUW1).withSummonerId(id).get();
         */
        
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = new SummonerBuilder().withPlatform(Platform.EUW1).withName(id).get();
        doAssertions.accept(s);
    }
    
    @Test
    public void testByName()
    {
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = new SummonerBuilder().withPlatform(Platform.EUW1).withName(id).get();
        
        Summoner optional = new SummonerBuilder().withPlatform(s.getPlatform()).withName(s.getNormalizedName()).get();
        doAssertions.accept(optional);
    }
    
    @Test
    public void testByAccount()
    {
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = new SummonerBuilder().withPlatform(Platform.EUW1).withName(id).get();
        
        Summoner optional = new SummonerBuilder().withPlatform(s.getPlatform()).withAccountId(s.getAccountId()).get();
        doAssertions.accept(optional);
    }
    
    @Test
    public void testByPUUID()
    {
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = new SummonerBuilder().withPlatform(Platform.EUW1).withName(id).get();
        
        Summoner optional = new SummonerBuilder().withPlatform(s.getPlatform()).withPUUID(s.getPUUID()).get();
        doAssertions.accept(optional);
    }
    
    @Test
    @Ignore
    public void testCrawler()
    {
        SummonerCrawler crawler = new SummonerCrawler(Summoner.byName(Platform.EUW1, "stelar7"));
        System.out.println(crawler.get().size());
        crawler.crawlLeague();
        System.out.println(crawler.get().size());
        
        crawler = new SummonerCrawler(Summoner.byName(Platform.EUW1, "stelar7"));
        System.out.println(crawler.get().size());
        crawler.crawlGames();
        System.out.println(crawler.get().size());
    }
}