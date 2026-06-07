package br.com.api.idrive.domain.infra.scheduler;

import br.com.api.idrive.domain.service.AulaServiceImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AulaScheduler {

    private static final Logger log = LoggerFactory.getLogger(AulaScheduler.class);

    private final AulaServiceImplement aulaService;

    public AulaScheduler(AulaServiceImplement aulaService) {
        this.aulaService = aulaService;
    }

    // Roda todo dia à meia-noite
    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    public void cancelarAnunciosVencidos() {
        log.info("[Scheduler] Iniciando cancelamento de anúncios vencidos...");
        aulaService.cancelarAnunciosVencidos();
        log.info("[Scheduler] Cancelamento concluído.");
    }
}