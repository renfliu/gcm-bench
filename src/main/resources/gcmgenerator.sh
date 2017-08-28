#!/usr/bin/env bash

function usage()
{
    echo "GCMGenerator is a tool for generating 'Global Catalogue of Microorganisms' "
    echo "simulated data. You can use this to generate data from 1M to 100G and as more "
    echo "as your computer can hold. The Global Catalogue of Microorganisms is composed "
    echo "of XXX parts, so you can change the ratio of each part to generating different "
    echo "data according to your request. The ratios you need is given as parameters of "
    echo "the command, and you can find the usage below."
    echo ""
    echo "SYNOPSIS:"
    echo "        gcmgenerator [OPTIONS]"
    echo ""
    echo "OPTIONS:"
    echo "        -o, --output  the file to save the output"
    echo ""

}

usage;

