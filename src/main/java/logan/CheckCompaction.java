package logan;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.config.ConfigResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CheckCompaction {

    public static void main (String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:19092");

        try (AdminClient adminClient = AdminClient.create(props)) {
            var topics = List.of(
                "gam_creative_city_stats",
                "gam_creative_metro_stats",
                "gam_creative_placement_stats",
                "gam_creative_targeting_stats",
                "gam_lineitem_city_stats",
                "gam_lineitem_postal_code_stats",
                "gam_lineitem_unique_stats_lifetime"
            );
            topics.forEach(t -> {
                try {
                    checkForTopic(adminClient, t);
                }
                catch (InterruptedException | ExecutionException e) {
                    log.error("Topic [{}] error", t, e);
                }
            });
        }
        catch (Exception e) {
            log.error("error", e);
        }
    }

    private static void checkForTopic (AdminClient adminClient, String topicName)
    throws InterruptedException, ExecutionException {
        DescribeTopicsResult result      = adminClient.describeTopics(Collections.singletonList(topicName));
        TopicDescription     description = result.topicNameValues().get(topicName).get();

        Config config = adminClient.describeConfigs(
                                       Collections.singletonList(new ConfigResource(ConfigResource.Type.TOPIC,
                                                                                    topicName
                                       ))
                                   ).all().get()
                                   .get(new ConfigResource(ConfigResource.Type.TOPIC, topicName));

        for (ConfigEntry entry : config.entries()) {
            if ( "cleanup.policy".equals(entry.name()) ) {
                log.info("[{}] Config [{}]:[{}]", topicName, entry.name(), entry.value());
                break;
            }
        }
    }

}
