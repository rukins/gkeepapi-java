package io.github.rukins.gkeepapi.model.gkeep;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class Timestamps {
    public static final String DEFAULT_DATETIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final LocalDateTime DEFAULT_LOCALDATETIME = LocalDateTime
            .of(1970, 1, 1, 0, 0);

    public static final ZoneId DEFAULT_ZONE_ID = ZoneOffset.UTC;

    private String kind;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime trashed;

    private LocalDateTime deleted;

    private LocalDateTime userEdited;

    private LocalDateTime recentSharedChangesSeen;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Timestamps timestamps;

        private Builder() {
            timestamps = new Timestamps();
        }

        public Builder created(LocalDateTime created) {
            timestamps.setCreated(created);
            return this;
        }

        public Builder updated(LocalDateTime updated) {
            timestamps.setUpdated(updated);
            return this;
        }

        public Builder trashed(LocalDateTime trashed) {
            timestamps.setTrashed(trashed);
            return this;
        }

        public Builder deleted(LocalDateTime deleted) {
            timestamps.setDeleted(deleted);
            return this;
        }

        public Builder userEdited(LocalDateTime userEdited) {
            timestamps.setUserEdited(userEdited);
            return this;
        }

        public Builder recentSharedChangesSeen(LocalDateTime recentSharedChangesSeen) {
            timestamps.setRecentSharedChangesSeen(recentSharedChangesSeen);
            return this;
        }

        public Timestamps build() {
            return timestamps;
        }
    }

    public String getKind() {
        return kind;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getTrashed() {
        return trashed;
    }

    public void setTrashed(LocalDateTime trashed) {
        this.trashed = trashed;
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getUserEdited() {
        return userEdited;
    }

    public void setUserEdited(LocalDateTime userEdited) {
        this.userEdited = userEdited;
    }

    public LocalDateTime getRecentSharedChangesSeen() {
        return recentSharedChangesSeen;
    }

    public void setRecentSharedChangesSeen(LocalDateTime recentSharedChangesSeen) {
        this.recentSharedChangesSeen = recentSharedChangesSeen;
    }

    @Override
    public String toString() {
        return "Timestamps{" +
                "kind='" + kind + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", trashed=" + trashed +
                ", deleted=" + deleted +
                ", userEdited=" + userEdited +
                ", recentSharedChangesSeen=" + recentSharedChangesSeen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timestamps that)) return false;
        return Objects.equals(created, that.created)
                && Objects.equals(updated, that.updated)
                && Objects.equals(trashed, that.trashed)
                && Objects.equals(deleted, that.deleted)
                && Objects.equals(userEdited, that.userEdited)
                && Objects.equals(recentSharedChangesSeen, that.recentSharedChangesSeen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, updated, trashed, deleted, userEdited, recentSharedChangesSeen);
    }
}
