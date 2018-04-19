pipelineJob('madcore.plugin.habitat.deploy') {
    parameters {
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Habitat: install'
                build job: 'madcore.habitat.install'
                stage 'Habitat Registry: setup'
                build job: 'madcore.habitat.registry.setup'
                }
            """.stripIndent())
	    }
    }
}
