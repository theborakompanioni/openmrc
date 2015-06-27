[![Build Status](https://travis-ci.org/theborakompanioni/openmrc.svg)](https://travis-ci.org/theborakompanioni/openmrc)

openmrc
===

### Download

#### Maven
```xml
<dependency>
    <groupId>org.tbk.openmrc</groupId>
    <artifactId>openmrc-core</artifactId>
    <version>${openmrc.version}</version>
</dependency>
<dependency>
    <groupId>org.tbk.openmrc</groupId>
    <artifactId>openmrc-web</artifactId>
    <version>${openmrc.version}</version>
</dependency>
```

```xml
<repositories>
    <repository>
        <id>tbk-openmrc-mvn-repo</id>
        <url>https://raw.github.com/theborakompanioni/openmrc/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```