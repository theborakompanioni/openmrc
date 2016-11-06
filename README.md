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

Enable snapshot repositories:
```xml
<profiles>
    <profile>
        <id>allow-snapshots</id>
        <activation><activeByDefault>true</activeByDefault></activation>
        <repositories>
            <repository>
                <id>snapshots-repo</id>
                <url>https://oss.sonatype.org/content/repositories/snapshots</url>
                <releases><enabled>false</enabled></releases>
                <snapshots><enabled>true</enabled></snapshots>
            </repository>
        </repositories>
    </profile>
</profiles>
```
