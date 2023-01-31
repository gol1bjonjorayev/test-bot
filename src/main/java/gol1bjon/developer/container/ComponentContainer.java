package gol1bjon.developer.container;

import gol1bjon.developer.bot.VotingBot;
import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.enums.AdminStatus;

import java.util.HashMap;
import java.util.Map;

public class ComponentContainer {
    public static VotingBot MY_BOT = null;
    public static String BOT_USERNAME = "test_uchun_faqat_uzbot";
    public static String BOT_TOKEN = "5748472466:AAHv-jFb82Ock5y1uaQ5fuYo5Nw-FHkSO5o";
    public static String ADMIN_CHAT_ID = "1807942859";

    public static boolean startElection = false;
    public static boolean finishElection = false;

    //adminChatId,AdminStatus
    public static Map<String, AdminStatus> adminStatusMap = new HashMap<>();

    public static Map<String, Candidate> candidateMap = new HashMap<>();

}
