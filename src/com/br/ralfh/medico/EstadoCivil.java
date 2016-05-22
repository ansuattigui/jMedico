package com.br.ralfh.medico;

/**
 *
 * @author Ralfh
 */
public enum EstadoCivil {
    
    casada {
        @Override
        public String estadocivil() {
            return "CASADA";
        }
    },
    casado {
        @Override
        public String estadocivil() {
            return "CASADO";
        }
    },
    divorciada {
        @Override
        public String estadocivil() {
            return "DIVORCIADA";
        }
    },
    divorciado {
        @Override
        public String estadocivil() {
            return "DIVORCIADO";
        }
    },
    solteira {
        @Override
        public String estadocivil() {
            return "SOLTEIRA";
        }
    },
    solteiro {
        @Override
        public String estadocivil() {
            return "SOLTEIRO";
        }
    },
    viuva {
        @Override
        public String estadocivil() {
            return "VIÚVA";
        }
    },
     viuvo {
        @Override
        public String estadocivil() {
            return "VIÚVO";
        }
    },
    uniao {
        @Override
        public String estadocivil() {
            return "UNIÃO ESTÁVEL";
        }
    };

    public abstract String estadocivil();    
    
//    CASADA,CASADO,DIVORCIADA,DIVORCIADO,SOLTEIRA,SOLTEIRO,VIÚVA,VIÚVO;
    
}
